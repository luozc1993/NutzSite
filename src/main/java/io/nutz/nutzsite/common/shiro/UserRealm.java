package io.nutz.nutzsite.common.shiro;

import io.nutz.nutzsite.common.exception.EmptyCaptchaException;
import io.nutz.nutzsite.common.exception.IncorrectCaptchaException;
import io.nutz.nutzsite.module.sys.models.User;
import io.nutz.nutzsite.module.sys.services.UserService;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.nutz.integration.shiro.AbstractSimpleAuthorizingRealm;
import org.nutz.integration.shiro.SimpleShiroToken;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.lang.Lang;
import org.nutz.lang.Strings;

import java.util.Set;

/**
 * 自定义Realm 处理登录 权限
 */
@IocBean(name = "shiroRealm", fields = "dao")
public class UserRealm extends AbstractSimpleAuthorizingRealm {
    @Inject
    private UserService userService;

    /**
     * 授权
     * @param principals
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        // null usernames are invalid
        if (principals == null) {
            throw new AuthorizationException("PrincipalCollection method argument cannot be null.");
        }
        String userId = String.valueOf(principals.getPrimaryPrincipal());
        User user = dao().fetch(User.class, userId);
        if (user == null) {
            return null;
        }
        // 角色列表
        Set<String> roles =userService.getRoleCodeList(user);
        // 功能列表
        Set<String> menus = userService.getMenuPermsList(user);

        SimpleAuthorizationInfo auth = new SimpleAuthorizationInfo();
		auth.setRoles(roles);
		auth.setStringPermissions(menus);
        return auth;
    }

    /**
     * 登录验证
     * @param token
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        CaptchaToken authcToken = (CaptchaToken) token;
        String loginname = authcToken.getUsername();
        String captcha = authcToken.getCaptcha();
        if (Strings.isBlank(loginname)) {
            throw Lang.makeThrow(AuthenticationException.class, "Account name is empty");
        }
        User user = dao().fetch(User.class,loginname);
        if (Lang.isEmpty(user)) {
            throw Lang.makeThrow(UnknownAccountException.class, "Account [ %s ] not found", loginname);
        }
        int errCount = NumberUtils.toInt(Strings.sNull(SecurityUtils.getSubject().getSession(true).getAttribute("errCount")));
        if (errCount > 2) {
            //输错三次显示验证码窗口
            if (Strings.isBlank(captcha)) {
                throw Lang.makeThrow(EmptyCaptchaException.class, "Captcha is empty");
            }
            String _captcha = Strings.sBlank(SecurityUtils.getSubject().getSession(true).getAttribute("captcha"));
            if (!authcToken.getCaptcha().equalsIgnoreCase(_captcha)) {
                throw Lang.makeThrow(IncorrectCaptchaException.class, "Captcha is error");
            }
        }
        if (user.isStatus()) {
            throw Lang.makeThrow(LockedAccountException.class, "Account [ %s ] is locked.", loginname);
        }
        //设置验证码次数为零
        SecurityUtils.getSubject().getSession(true).setAttribute("errCount", 0);
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(user, user.getPassword(), getName());
        info.setCredentialsSalt(ByteSource.Util.bytes(user.getSalt()));
        return info;
    }

    public UserRealm() {
        this(null, null);
    }

    public UserRealm(CacheManager cacheManager, CredentialsMatcher matcher) {
        super(cacheManager, matcher);
        setAuthenticationTokenClass(SimpleShiroToken.class);
    }

    public UserRealm(CacheManager cacheManager) {
        this(cacheManager, null);
    }

    public UserRealm(CredentialsMatcher matcher) {
        this(null, matcher);
    }

}