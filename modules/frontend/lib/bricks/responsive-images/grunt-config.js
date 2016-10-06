/* brick 'responsive images' */
module.exports = function (grunt, options) {
  'use strict';

  // add templates to theme templateset
  var existingTemplates = grunt.config.get('compress.templates.files');
  existingTemplates.push({
    expand: true,
    cwd: options.brickDirectory + '/templates',
    src: '**',
    dest: 'META-INF/resources/WEB-INF/templates/bricks/'
  });

  return {
    tasks: {
      compress: {
        templates: {
          files: existingTemplates
        }
      },
      copy: {
        brick_responsiveImages: {
          files: [
            // copy files
            {
              expand: true,
              isFile: true,
              cwd: options.brickDirectory,
              src: ['css/**', 'fonts/**', 'img/**', 'js/**', 'vendor/**', '*.properties'],
              dest: '../../target/resources/themes/<%= themeConfig.name %>'
            },
            // copy templates
            {
              expand: true,
              cwd: options.brickDirectory + '/templates',
              src: '**',
              dest: options.brickTemplatesDest
            }]
        }
      },
      watch: {
        brick_responsiveImages: {
          files: [options.brickDirectory + "**"],
          tasks: ['copy:brick_responsiveImages']
        }
      }
    }
  };
};
