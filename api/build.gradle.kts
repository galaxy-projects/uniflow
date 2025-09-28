dependencies {
    compileOnly(libs.annotations)
}

tasks.test {
    useJUnitPlatform()
}