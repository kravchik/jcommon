package yk.lang.yads;

import yk.jcommon.collections.YList;

import java.util.List;

public interface YadsAware {

    //Object yadsDeserialize(Object body);

    //return null if want default serialization
    //String yadsSerialize();
    //return null if want default serialization
    //String yadsSerializeWhenTypeIsKnown();
    YList serializeInner();

    List yadsSerialize(boolean typeIsKnown);

    //TODO do we want serialize to methodCall instead of serializeInner?

}
