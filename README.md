## Getting Started

Welcome to the VS Code Java world. Here is a guideline to help you get started to write Java code in Visual Studio Code.

## Folder Structure

The workspace contains two folders by default, where:

- `src`: the folder to maintain sources
- `lib`: the folder to maintain dependencies

Meanwhile, the compiled output files will be generated in the `bin` folder by default.

> If you want to customize the folder structure, open `.vscode/settings.json` and update the related settings there.

## Dependency Management

The `JAVA PROJECTS` view allows you to manage your dependencies. More details can be found [here](https://github.com/microsoft/vscode-java-dependency#manage-dependencies).


javadoc -d docs/ -classpath lib/graphics/lwjgl-opengl/lwjgl-opengl.jar:lib/graphics/lwjgl-glfw/lwjgl-glfw.jar:lib/graphics/lwjgl/lwjgl.jar:lib/graphics/lwjgl-stb/lwjgl-stb.jar:lib/math/joml-1.10.5.jar -sourcepath src/main/java -overview html/overview.html -author -subpackages com.quantum



javadoc -d docs/ -classpath lib/graphics/lwjgl-opengl/lwjgl-opengl.jar:lib/graphics/lwjgl-glfw/lwjgl-glfw.jar:lib/graphics/lwjgl/lwjgl.jar:lib/graphics/lwjgl-stb/lwjgl-stb.jar:lib/math/joml-1.10.5.jar -sourcepath src/main/java -overview html/overview.html -author -version -subpackages com.quantum -link lib-javadoc/lwjgl-glfw-javadoc -link lib-javadoc/lwjgl-javadoc -link lib-javadoc/lwjgl-opengl-javadoc -link lib-javadoc/lwjgl-stb-javadoc



com.quantum.core com.quantum.core.graphics com.quantum.core.io com.quantum.core.logic com.quantum.entity com.quantum.entity.light com.quantum.ui com.quantum.ui.event com.quantum.system com.quantum.launcher com.quantum.utils com.quantum.utils.config com.quantum.utils.logging 

javadoc -d docs/ -classpath lib/graphics/lwjgl-opengl/lwjgl-opengl.jar:lib/graphics/lwjgl-glfw/lwjgl-glfw.jar:lib/graphics/lwjgl/lwjgl.jar:lib/graphics/lwjgl-stb/lwjgl-stb.jar:lib/math/joml-1.10.5.jar -link lib/graphics/lwjgl-glfw/lwjgl-glfw-javadoc.jar:lib/graphics/lwjgl/lwjgl-javadoc.jar:lib/graphics/lwjgl-opengl/lwjgl-opengl-javadoc.jar:lib/graphics/lwjgl-stb/lwjgl-stb-javadoc.jar -author -sourcepath src/main/java -overview html/overview.html -subpackages com.quantum

javadoc -d docs/ -classpath lib/graphics/lwjgl-opengl/lwjgl-opengl.jar:lib/graphics/lwjgl-glfw/lwjgl-glfw.jar:lib/graphics/lwjgl/lwjgl.jar:lib/graphics/lwjgl-stb/lwjgl-stb.jar:lib/math/joml-1.10.5.jar -sourcepath src/main/java com.quantum.entity com.quantum.entity.light 



javadoc -d docs/ -classpath lib/graphics/lwjgl-opengl/lwjgl-opengl.jar:lib/graphics/lwjgl-glfw/lwjgl-glfw.jar:lib/graphics/lwjgl/lwjgl.jar:lib/graphics/lwjgl-stb/lwjgl-stb.jar:lib/math/joml-1.10.5.jar -sourcepath src/main/java com.quantum.ui com.quantum.ui.event



javadoc -d docs/ -classpath lib/graphics/lwjgl-opengl/lwjgl-opengl.jar:lib/graphics/lwjgl-glfw/lwjgl-glfw.jar:lib/graphics/lwjgl/lwjgl.jar:lib/graphics/lwjgl-stb/lwjgl-stb.jar:lib/math/joml-1.10.5.jar -sourcepath src/main/java com.quantum.system 



javadoc -d docs/ -classpath lib/graphics/lwjgl-opengl/lwjgl-opengl.jar:lib/graphics/lwjgl-glfw/lwjgl-glfw.jar:lib/graphics/lwjgl/lwjgl.jar:lib/graphics/lwjgl-stb/lwjgl-stb.jar:lib/math/joml-1.10.5.jar -sourcepath src/main/java com.quantum.launcher



javadoc -d docs/ -classpath lib/graphics/lwjgl-opengl/lwjgl-opengl.jar:lib/graphics/lwjgl-glfw/lwjgl-glfw.jar:lib/graphics/lwjgl/lwjgl.jar:lib/graphics/lwjgl-stb/lwjgl-stb.jar:lib/math/joml-1.10.5.jar -sourcepath src/main/java com.quantum.utils com.quantum.utils.config com.quantum.utils.logging