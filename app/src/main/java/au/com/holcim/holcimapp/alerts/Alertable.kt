package au.com.holcim.holcimapp.alerts

/**
 * Created by chowii on 26/05/17.
 */

internal interface Alertable {
    val createdAt: String
    val alertType: String
    val additionalMessage: String
    val address: String
    val alertAttachment: AlertAttachment
    val id: Int
}
