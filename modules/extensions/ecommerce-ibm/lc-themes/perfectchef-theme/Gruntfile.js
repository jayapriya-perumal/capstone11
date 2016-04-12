module.exports = function (grunt) {
  'use strict';

  // Force use of Unix newlines
  grunt.util.linefeed = '\n';

  // These plugins provide necessary tasks.
  require('load-grunt-tasks')(grunt, {scope: 'devDependencies'});
  require('time-grunt')(grunt);

  // --- Project configuration ---
  grunt.initConfig({

    // --- Properties ---
    pkg: grunt.file.readJSON('package.json'),
    distDir: 'target/resources/themes/perfectchef',

    // --- Task configuration ---
    clean: {
      options: {
        force: true
      },
      build: ['<%=  distDir %>']
    },
    copy: {
      main: {
        files: [{
          expand: true,
          cwd: 'src/',
          src: ['css/**', 'fonts/**', 'img/**', 'js/**', 'vendor/**'],
          dest: '<%=  distDir %>/'
        }]
      }
    },
    compress: {
      templates: {
        options: {
          archive: '<%=  distDir %>/templates/perfectchef-templates.jar',
          mode: 'zip'
        },
        expand: true,
        dot: true,
        cwd: 'src/main/resources/',
        src: ['**']
      }
    },
    watch: {
      options: {
        livereload: true // default port: 35729
      },
      sources: {
        files: 'src/**/*.*',
        tasks: ['copy']
      }
    }
  });

  // --- Tasks ---

  // Full distribution task without templates.
  grunt.registerTask('build', ['clean', 'copy']);

  // Full distribution task with templates.
  grunt.registerTask('buildWithTemplates', ['build', 'compress:templates']);

  // Default task = distribution.
  grunt.registerTask('default', ['build']);
};
