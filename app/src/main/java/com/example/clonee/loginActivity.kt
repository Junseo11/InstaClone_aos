package com.example.clonee

import android.content.Intent
import android.os.Bundle
import android.preference.PreferenceManager.OnActivityResultListener
import android.util.Log
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.clonee.databinding.ActivityLoginBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class loginActivity : AppCompatActivity() {

    lateinit var auth: FirebaseAuth
    lateinit var binding: ActivityLoginBinding
    lateinit var googleSininClient: GoogleSignInClient // 구글 로그인 클라이언트
    private lateinit var GoogleSingResultLauncher: ActivityResultLauncher<Intent>


    var GOOGLE_LOGIN_CODE = 9001
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            //.requestIdToken("88594815458-v3k0g4isjo5eahn0ra32q7g8v542pv88.apps.googleusercontent.com")
            .requestEmail()
            .build()

        googleSininClient = GoogleSignIn.getClient(this,gso)

        binding.emailLoginbtn.setOnClickListener {
            signinAndSignup() // 로그인 회원가입
        }

        binding.googleLogin.setOnClickListener{
            var signIntent: Intent = googleSininClient.getSignInIntent()
            GoogleSingResultLauncher.launch(signIntent)

        }
        GoogleSingResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){ result:ActivityResult ->
            if(result.resultCode == RESULT_OK){
                result.data?.let{data->
                    val task = GoogleSignIn.getSignedInAccountFromIntent(data)
                    handleSignInResult(task)

                    try {
                        val account = task.getResult(ApiException::class.java)!!

                        println(account.id)
                    }catch (e: ApiException){
                        println(e)
                    }

                }
            }else{
                println(result)
            }
        }
            
    }

    fun handleSignInResult(completedTask: Task<GoogleSignInAccount>){
        try {
            val account = completedTask.getResult(ApiException::class.java)
            val email = account?.email.toString()
            var googletoken = account?.idToken.toString()

            Log.e("Google acc",email)
            Log.e("acc",googletoken)

        }catch (e:ApiException){
            Log.e("s", e.statusCode.toString())
            println("실패")
        }
    }



        //task ->Task로 쓰고 함수 변경하니 해결
        fun signinAndSignup() {
            var id = binding.emailEdi.text.toString()
            var password = binding.passwordedit.text.toString()



            //회원가입
            auth.createUserWithEmailAndPassword(id, password).addOnCompleteListener { Task ->
                if (Task.isSuccessful) { //회원가입 성공시 메인으로 이동
                    if (auth.currentUser!!.isEmailVerified) {  //이부분 다시
                        moveMain(auth.currentUser)
                    }
                } else { // 이미 계정이 있으면 로그인 완료
                    signinEmail()
                }
            }
        }

        fun signinEmail() {
            var id = binding.emailEdi.text.toString()
            var password = binding.passwordedit.text.toString()
            auth.signInWithEmailAndPassword(id, password)
                .addOnCompleteListener { Task -> // 이메일 패스워드 맞았을때 메인으로 이동
                    if (Task.isSuccessful) {
                        moveMain(Task.result?.user)
                    }
                }
        }

        // 메인엑티비티로 이동하는 코드
        fun moveMain(user: FirebaseUser?) {
            if (user != null) {   //유저가 널이아니고 이메일이 맞으면 메인 엑티비티로 이동
                if (user.isEmailVerified) {
                    finish()
                    startActivity(Intent(this, MainActivity::class.java))
                }
            }
        }


    }










