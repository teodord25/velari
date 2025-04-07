use jni::JNIEnv;
use jni::objects::JClass;
use jni::sys::jstring;
use jni::sys::jdouble;

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

// Function to expose compute thing
#[unsafe(no_mangle)]
pub extern "system" fn Java_com_github_teodord25_velari_OrbitEngine_computeDistance(
    _env: JNIEnv,
    _class: JClass,
    x: jdouble,
    y: jdouble,
    z: jdouble,
) -> jdouble {
    compute_distance(x as f64, y as f64, z as f64)
}

/// Regular Rust function that computes the distance.
fn compute_distance(x: f64, y: f64, z: f64) -> f64 {
    (x * x + y * y + z * z).sqrt()
}
