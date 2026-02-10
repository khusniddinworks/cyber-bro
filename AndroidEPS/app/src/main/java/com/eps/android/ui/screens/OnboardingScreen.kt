package com.eps.android.ui.screens

import android.Manifest
import android.os.Build
import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.VerifiedUser
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.eps.android.R
import com.eps.android.ui.theme.*
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun OnboardingScreen(
    onFinish: () -> Unit,
    onLanguageSelected: (String) -> Unit
) {
    var currentStep by rememberSaveable { mutableIntStateOf(0) }
    val totalSteps = 4
    
    val permissionsState = rememberMultiplePermissionsState(
        permissions = mutableListOf(
            Manifest.permission.POST_NOTIFICATIONS
        ).apply {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.R) {
                add(Manifest.permission.READ_EXTERNAL_STORAGE)
                add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
            }
        }
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(OnyxBlack)
    ) {
        AnimatedContent(
            targetState = currentStep,
            label = "Onboarding",
            transitionSpec = {
                slideInHorizontally { it } + fadeIn() togetherWith slideOutHorizontally { -it } + fadeOut()
            }
        ) { step ->
            when (step) {
                0 -> LanguageSelectionSlide(
                    onNext = { lang ->
                        onLanguageSelected(lang)
                        currentStep = 1
                    }
                )
                1 -> IntroSlide(
                    title = stringResource(R.string.intro_title),
                    desc = stringResource(R.string.intro_desc),
                    icon = Icons.Default.Face,
                    onNext = { currentStep = 2 }
                )
                2 -> IntroSlide(
                    title = stringResource(R.string.network_title),
                    desc = stringResource(R.string.network_desc),
                    icon = Icons.Default.Shield,
                    onNext = { currentStep = 3 }
                )
                3 -> PermissionSlide(
                    onNext = { 
                        permissionsState.launchMultiplePermissionRequest()
                        onFinish() 
                    }
                )
            }
        }
        
        // Step Indicator
        Row(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 40.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            repeat(totalSteps) { index ->
                val width = if (currentStep == index) 24.dp else 8.dp
                val color = if (currentStep == index) GoldPremium else Color.Gray.copy(alpha=0.3f)
                
                Box(
                    modifier = Modifier
                        .height(8.dp)
                        .width(width)
                        .clip(CircleShape)
                        .background(color)
                )
            }
        }
    }
}

@Composable
fun LanguageSelectionSlide(onNext: (String) -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize().padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = stringResource(R.string.select_language),
            color = GoldPremium,
            fontSize = 11.sp,
            fontWeight = FontWeight.Black,
            letterSpacing = 2.sp
        )
        
        Spacer(modifier = Modifier.height(48.dp))
        
        LanguageBtn("🇺🇿 O\'zbekcha", onNext, "uz")
        Spacer(modifier = Modifier.height(16.dp))
        LanguageBtn("🇺🇸 English", onNext, "en")
        Spacer(modifier = Modifier.height(16.dp))
        LanguageBtn("🇷🇺 Русский", onNext, "ru")
    }
}

@Composable
fun LanguageBtn(label: String, onClick: (String) -> Unit, code: String) {
    Button(
        onClick = { onClick(code) },
        modifier = Modifier.fillMaxWidth().height(60.dp),
        colors = ButtonDefaults.buttonColors(containerColor = GlassSurface),
        shape = RoundedCornerShape(16.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, Color.White.copy(alpha=0.05f))
    ) {
        Text(label, color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
    }
}

@Composable
fun IntroSlide(title: String, desc: String, icon: ImageVector, onNext: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize().padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier.size(160.dp).background(GoldPremium.copy(alpha=0.05f), CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = GoldPremium,
                modifier = Modifier.size(80.dp)
            )
        }
        
        Spacer(modifier = Modifier.height(48.dp))
        
        Text(
            text = title,
            color = Color.White,
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            lineHeight = 34.sp
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        Text(
            text = desc,
            color = Color.Gray,
            fontSize = 15.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 20.dp)
        )
        
        Spacer(modifier = Modifier.height(64.dp))
        
        Button(
            onClick = onNext,
            modifier = Modifier.fillMaxWidth().height(56.dp),
            colors = ButtonDefaults.buttonColors(containerColor = GoldPremium, contentColor = OnyxBlack),
            shape = RoundedCornerShape(16.dp)
        ) {
            Text(stringResource(R.string.btn_continue), fontWeight = FontWeight.Black)
            Spacer(modifier = Modifier.width(8.dp))
            Icon(Icons.Default.ArrowForward, contentDescription = null)
        }
    }
}

@Composable
fun PermissionSlide(onNext: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize().padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier.size(160.dp).background(Color.Red.copy(alpha=0.05f), CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.VerifiedUser,
                contentDescription = null,
                tint = GoldPremium,
                modifier = Modifier.size(80.dp)
            )
        }
        
        Spacer(modifier = Modifier.height(48.dp))
        
        Text(
            text = stringResource(R.string.permission_required),
            color = GoldPremium,
            fontSize = 11.sp,
            fontWeight = FontWeight.Black,
            letterSpacing = 2.sp
        )
        
        Text(
            text = stringResource(R.string.full_protection),
            color = Color.White,
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        Text(
            text = stringResource(R.string.permission_desc),
            color = Color.Gray,
            fontSize = 15.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 20.dp)
        )
        
        Spacer(modifier = Modifier.height(64.dp))
        
        Button(
            onClick = onNext,
            modifier = Modifier.fillMaxWidth().height(56.dp),
            colors = ButtonDefaults.buttonColors(containerColor = GoldPremium, contentColor = OnyxBlack),
            shape = RoundedCornerShape(16.dp)
        ) {
            Text(stringResource(R.string.btn_grant), fontWeight = FontWeight.Black)
        }
    }
}
