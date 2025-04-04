use jni::JNIEnv;
use jni::objects::JClass;
use jni::sys::jstring;

/// This is the function Java will call
#[unsafe(no_mangle)]
pub extern "system" fn Java_com_github_teodord25_velari_OrbitEngine_checkBridge(
    env: JNIEnv,
    _class: JClass,
) -> jstring {
    env.new_string("Hello from Rust!")
       .expect("Couldn't create Java string!")
       .into_raw()
}
