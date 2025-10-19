dependencies {
    compileOnly(libs.annotations)
    compileOnly(libs.lombok)
    annotationProcessor(libs.lombok)

    compileOnly(project(":api"))
    compileOnly(project(":common"))
}