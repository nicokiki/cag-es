package com.clickandgolf



import org.junit.*
import grails.test.mixin.*

@TestFor(ScorecardmetadataController)
@Mock(Scorecardmetadata)
class ScorecardmetadataControllerTests {


    def populateValidParams(params) {
      assert params != null
      // TODO: Populate valid properties like...
      //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/scorecardmetadata/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.scorecardmetadataInstanceList.size() == 0
        assert model.scorecardmetadataInstanceTotal == 0
    }

    void testCreate() {
       def model = controller.create()

       assert model.scorecardmetadataInstance != null
    }

    void testSave() {
        controller.save()

        assert model.scorecardmetadataInstance != null
        assert view == '/scorecardmetadata/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/scorecardmetadata/show/1'
        assert controller.flash.message != null
        assert Scorecardmetadata.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/scorecardmetadata/list'


        populateValidParams(params)
        def scorecardmetadata = new Scorecardmetadata(params)

        assert scorecardmetadata.save() != null

        params.id = scorecardmetadata.id

        def model = controller.show()

        assert model.scorecardmetadataInstance == scorecardmetadata
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/scorecardmetadata/list'


        populateValidParams(params)
        def scorecardmetadata = new Scorecardmetadata(params)

        assert scorecardmetadata.save() != null

        params.id = scorecardmetadata.id

        def model = controller.edit()

        assert model.scorecardmetadataInstance == scorecardmetadata
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/scorecardmetadata/list'

        response.reset()


        populateValidParams(params)
        def scorecardmetadata = new Scorecardmetadata(params)

        assert scorecardmetadata.save() != null

        // test invalid parameters in update
        params.id = scorecardmetadata.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/scorecardmetadata/edit"
        assert model.scorecardmetadataInstance != null

        scorecardmetadata.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/scorecardmetadata/show/$scorecardmetadata.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        scorecardmetadata.clearErrors()

        populateValidParams(params)
        params.id = scorecardmetadata.id
        params.version = -1
        controller.update()

        assert view == "/scorecardmetadata/edit"
        assert model.scorecardmetadataInstance != null
        assert model.scorecardmetadataInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/scorecardmetadata/list'

        response.reset()

        populateValidParams(params)
        def scorecardmetadata = new Scorecardmetadata(params)

        assert scorecardmetadata.save() != null
        assert Scorecardmetadata.count() == 1

        params.id = scorecardmetadata.id

        controller.delete()

        assert Scorecardmetadata.count() == 0
        assert Scorecardmetadata.get(scorecardmetadata.id) == null
        assert response.redirectedUrl == '/scorecardmetadata/list'
    }
}
