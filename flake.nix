{
  description = "Dev shell for Velari (Minecraft mod with Java + Rust + JDTLS)";

  inputs = {
    nixpkgs.url = "github:NixOS/nixpkgs/nixos-unstable";
    flake-utils.url = "github:numtide/flake-utils";
  };

  outputs = { self, nixpkgs, flake-utils }:
    flake-utils.lib.eachDefaultSystem (system:
      let
        pkgs = nixpkgs.legacyPackages.${system};
      in {
        devShell = pkgs.mkShell {
          name = "velari-dev-shell";

          buildInputs = [
            # Java dev env
            pkgs.openjdk21
            pkgs.gradle
            pkgs.jdt-language-server

            # Rust toolchain
            pkgs.rustc
            pkgs.cargo
            pkgs.clippy
            pkgs.rustfmt

            # Native dependencies for GLFW / AWT / OpenGL
            pkgs.glfw
            pkgs.mesa
            pkgs.libGL
            pkgs.xorg.libX11
            pkgs.xorg.libXext
            pkgs.xorg.libXrandr
            pkgs.xorg.libXcursor
            pkgs.xorg.libXi
            pkgs.xorg.libXxf86vm
            pkgs.xorg.libXinerama
            pkgs.xorg.libXcomposite
            pkgs.xorg.libXdamage
            pkgs.xorg.libXfixes
            pkgs.xorg.libXtst
            pkgs.xorg.libxcb
          ];

          shellHook = ''
            echo "🪐 Welcome to Velari dev shell"
            echo "Java, Rust, Gradle, and JDTLS are ready."
            echo "Use './gradlew runClient' to test the mod."

            export LD_LIBRARY_PATH=${pkgs.lib.makeLibraryPath [
              pkgs.xorg.libX11
              pkgs.xorg.libXext
              pkgs.xorg.libXrandr
              pkgs.xorg.libXcursor
              pkgs.xorg.libXi
              pkgs.xorg.libXxf86vm
              pkgs.xorg.libXinerama
              pkgs.xorg.libXcomposite
              pkgs.xorg.libXdamage
              pkgs.xorg.libXfixes
              pkgs.xorg.libXtst
              pkgs.xorg.libxcb
              pkgs.libGL
              pkgs.glfw
              pkgs.mesa
            ]}:$LD_LIBRARY_PATH
          '';
        };
      });
}
