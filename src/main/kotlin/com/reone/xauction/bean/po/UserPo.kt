package com.reone.xauction.bean.po

import javax.persistence.*

/**
 *
 * @author reone
 * @date 2025/7/17 16:43
 */
@Entity
@Table(name = "xa_user")
open class UserPo(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    open var id: Long? = null,

    @Column(nullable = false, unique = true)
    open var code: String? = null,

    @Column(nullable = true)
    open var name: String? = null,

    @Column(nullable = true)
    open var nick: String? = null,

    @Column(nullable = true)
    open var phone: String? = null,

    @Column(nullable = true)
    open var avatar: String? = null,

    @Column(nullable = true)
    open var loginTime: Long? = null,

    @Column(nullable = true)
    open var password: String? = null,

    ) : BasePo() {
    fun copyNotNull(form: UserPo) {
        form.name?.let { name = it }
        form.phone?.let { phone = it }
        form.nick?.let { nick = it }
        form.avatar?.let { avatar = it }
    }
}