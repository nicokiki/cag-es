package com.clickandgolf



import org.junit.*
import grails.test.mixin.*

@TestFor(ScorecardController)
@Mock(Scorecard)
class ScorecardControllerTests {


    def populateValidParams(params) {
      assert params != null
      // TODO: Populate valid properties like...
      //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/scorecard/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.scorecardInstanceList.size() == 0
        assert model.scorecardInstanceTotal == 0
    }

    void testCreate() {
       def model = controller.create()

       assert model.scorecardInstance != null
    }

    void testSave() {
        controller.save()

        assert model.scorecardInstance != null
        assert view == '/scorecard/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/scorecard/show/1'
        assert controller.flash.message != null
        assert Scorecard.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/scorecard/list'


        populateValidParams(params)
        def scorecard = new Scorecard(params)

        assert scorecard.save() != null

        params.id = scorecard.id

        def model = controller.show()

        assert model.scorecardInstance == scorecard
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/scorecard/list'


        populateValidParams(params)
        def scorecard = new Scorecard(params)

        assert scorecard.save() != null

        params.id = scorecard.id

        def model = controller.edit()

        assert model.scorecardInstance == scorecard
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/scorecard/list'

        response.reset()


        populateValidParams(params)
        def scorecard = new Scorecard(params)

        assert scorecard.save() != null

        // test invalid parameters in update
        params.id = scorecard.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/scorecard/edit"
        assert model.scorecardInstance != null

        scorecard.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/scorecard/show/$scorecard.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        scorecard.clearErrors()

        populateValidParams(params)
        params.id = scorecard.id
        params.version = -1
        controller.update()

        assert view == "/scorecard/edit"
        assert model.scorecardInstance != null
        assert model.scorecardInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/scorecard/list'

        response.reset()

        populateValidParams(params)
        def scorecard = new Scorecard(params)

        assert scorecard.save() != null
        assert Scorecard.count() == 1

        params.id = scorecard.id

        controller.delete()

        assert Scorecard.count() == 0
        assert Scorecard.get(scorecard.id) == null
        assert response.redirectedUrl == '/scorecard/list'
    }
}
