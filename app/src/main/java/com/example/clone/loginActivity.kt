package com.example.clone

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.clone.databinding.ActivityLoginBinding
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.ktx.Firebase


class loginActivity : AppCompatActivity() {

    lateinit var auth : FirebaseAuth

    lateinit var binding :ActivityLoginBinding
    lateinit var googleSininClient :GoogleSignInClient


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()


        binding.emailLoginbtn.setOnClickListener {
            signinAndSignup()
        }



    }

    //task ->Task로 쓰고 함수 변경하니 해결
    fun signinAndSignup(){
        var id = binding.emailEdi.text.toString()
        var password = binding.passwordedit.text.toString()

        //회원가입
       auth.createUserWithEmailAndPassword(id,password).addOnCompleteListener {
            Task ->
            if(Task.isSuccessful){ //회원가입 성공시 메인으로 이동
                if(auth.currentUser!!.isEmailVerified){  //이부분 다시
                    moveMain(auth.currentUser)
                }
            }
            else{ // 이미 계정이 있으면 로그인 완료
                signinEmail()
            }
        }
    }

    fun signinEmail(){
        var id = binding.emailEdi.text.toString()
        var password = binding.passwordedit.text.toString()
        auth.signInWithEmailAndPassword(id,password).addOnCompleteListener {
            Task -> // 이메일 패스워드 맞았을때 메인으로 이동
            if(Task.isSuccessful){
                moveMain(Task.result?.user)
            }
        }
    }


    
    // 메인엑티비티로 이동하는 코드 
    fun moveMain(user :FirebaseUser?){
        if(user != null){   //유저가 널이아니고 이메일이 맞으면 메인 엑티비티로 이동
            if(user.isEmailVerified){
                finish()
                startActivity(Intent(this,MainActivity::class.java))
            }
        }
    }







}








