var gulp = require('gulp');
var concat = require('gulp-concat');
var concatVendor = require('gulp-concat-vendor');
var inject = require('gulp-inject');
var mainBowerFiles = require('main-bower-files');
var runSequence = require('run-sequence');
var series = require('stream-series');
var install = require('gulp-install');

var vendorJs;
var sourceJs;
var templatesJs;

var resourcesDirectory = '../resources/static/';

gulp.task('install', function() {
    gulp.src(['./package.json', './bower.json'])
        .pipe(install());
});

gulp.task('lib-js-files', function() {
    vendorJs = gulp.src(mainBowerFiles('**/*.js'), {base: 'bower_components'})
        .pipe(concatVendor('lib.min.js'))
        .pipe(gulp.dest(resourcesDirectory + 'js'));
});

gulp.task('source-js-files', function() {
    sourceJs = gulp.src('app/**/**/*.js')
        .pipe(concat('script.min.js'))
        .pipe(gulp.dest(resourcesDirectory + 'js'));
});

gulp.task('templates', function() {
    templatesJs = gulp.src('app/**/**/*.html')
        .pipe(gulp.dest(resourcesDirectory + 'views/'));
});

gulp.task('index', function() {
    var target = gulp.src(resourcesDirectory + 'index.html');
    return target.pipe(inject(series(templatesJs, vendorJs, sourceJs), {relative: true}))
        .pipe(gulp.dest(resourcesDirectory));
});

gulp.task('default', function() {
    runSequence('install', 'lib-js-files', 'templates', 'source-js-files', 'index');
});