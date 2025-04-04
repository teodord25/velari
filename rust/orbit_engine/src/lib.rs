use jni::JNIEnv;
use jni::objects::JClass;
use jni::sys::jstring;

/// This is the function Java will call
#[unsafe(no_mangle)]
pub extern "system" fn Java_com_velari_OrbitEngine_checkBridge(
    env: JNIEnv,
    _class: JClass,
) -> jstring {
    let output = env
        .new_string("Hello from Rust!")
        .expect("Couldn't create Java string!");
    output.into_raw()
}
