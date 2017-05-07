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
var ownLib;

var resourcesDirectory = '../resources/static/';
var outputDirectory = '../../../target/classes/static/';
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
/*---------------------Build tasks-------------------------------------*/
gulp.task('lib-common-js-build', function () {
    gulp.src(resourcesDirectory + 'js/common.js')
        .pipe(gulp.dest(outputDirectory + 'js/'));
});

gulp.task('lib-js-files-build', function() {
    vendorJs = gulp.src(mainBowerFiles('**/*.js'), {base: 'bower_components'})
        .pipe(concatVendor('lib.min.js'))
        .pipe(gulp.dest(outputDirectory + 'js'));
});

gulp.task('source-js-files-build', function() {
    sourceJs = gulp.src('app/**/**/*.js')
        .pipe(concat('script.min.js'))
        .pipe(gulp.dest(outputDirectory + 'js'));
});

gulp.task('templates-build', function() {
    templatesJs = gulp.src('app/**/**/*.html')
        .pipe(gulp.dest(outputDirectory + 'views/'));
});

gulp.task('default-build', function() {
    runSequence('lib-common-js-build', 'lib-js-files-build', 'templates-build', 'source-js-files-build');
});
/*---------------------------------------------------------*/
/*----------------------Union default task-----------------*/
gulp.task('default-union', function () {
    runSequence('default', 'default-build');
});
/*--------------------------------------------------------*/