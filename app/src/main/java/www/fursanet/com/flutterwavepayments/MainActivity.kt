package www.fursanet.com.flutterwavepayments

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.flutterwave.raveandroid.RaveConstants
import com.flutterwave.raveandroid.RavePayActivity
import com.flutterwave.raveandroid.RavePayManager
import java.util.*

class MainActivity : AppCompatActivity(), View.OnClickListener {
    var btnOne: Button? = null
    var btnTwo: Button? = null
    val amount_1 = 5000
    val amount_2 = 2500
    var email = ""
    var fName = ""
    var lName = ""
    var narration = "payment for food"
    var txRef: String? = null
    var country = "TZ"
    var currency = "TZS"
    val publicKey = "" //Get your public key from your account
    val encryptionKey = "" //Get your encryption key from your account
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btnOne = findViewById(R.id.btn_one)
        btnOne.setOnClickListener(this)
        btnTwo = findViewById(R.id.btn_two)
        btnTwo.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.btn_one -> makePayment(amount_1) //calls payment method with amount 1
            R.id.btn_two -> makePayment(amount_2) //calls payment method with amount 2
        }
    }

    fun makePayment(amount: Int) {
        txRef = email + " " + UUID.randomUUID().toString()

        /*
        Create instance of RavePayManager
         */RavePayManager(this).setAmount(amount.toDouble())
                .setCountry(country)
                .setCurrency(currency)
                .setEmail(email)
                .setfName(fName)
                .setlName(lName)
                .setNarration(narration)
                .setPublicKey(publicKey)
                .setEncryptionKey(encryptionKey)
                .setTxRef(txRef)
                .acceptAccountPayments(true)
                .acceptCardPayments(
                        true)
                .acceptMpesaPayments(true)
                .acceptGHMobileMoneyPayments(true)
                .onStagingEnv(false).allowSaveCardFeature(true)
                .withTheme(R.style.DefaultPayTheme)
                .initialize()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RaveConstants.RAVE_REQUEST_CODE && data != null) {
            val message = data.getStringExtra("response")
            if (resultCode == RavePayActivity.RESULT_SUCCESS) {
                Toast.makeText(this, "SUCCESS $message", Toast.LENGTH_SHORT).show()
            } else if (resultCode == RavePayActivity.RESULT_ERROR) {
                Toast.makeText(this, "ERROR $message", Toast.LENGTH_SHORT).show()
            } else if (resultCode == RavePayActivity.RESULT_CANCELLED) {
                Toast.makeText(this, "CANCELLED $message", Toast.LENGTH_SHORT).show()
            }
        }
    }
}