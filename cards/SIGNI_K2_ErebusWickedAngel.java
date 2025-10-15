package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class SIGNI_K2_ErebusWickedAngel extends Card {
    
    public SIGNI_K2_ErebusWickedAngel()
    {
        setImageSets("WXDi-P02-084");
        
        setOriginalName("凶天　エレボス");
        setAltNames("キョウテンエレボス Kyouten Erebosu");
        setDescription("jp",
                "@E：ターン終了時まで、対戦相手のすべてのシグニのパワーを－1000する。"
        );
        
        setName("en", "Erebus, Doomed Angel");
        setDescription("en",
                "@E: All SIGNI on your opponent's field get --1000 power until end of turn."
        );
        
        setName("en_fan", "Erebus, Wicked Angel");
        setDescription("en_fan",
                "@E: Until end of turn, all of your opponent's SIGNI get --1000 power."
        );
        
		setName("zh_simplified", "凶天 厄瑞玻斯");
        setDescription("zh_simplified", 
                "@E :直到回合结束时为止，对战对手的全部的精灵的力量-1000。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.BLACK);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.ANGEL);
        setLevel(2);
        setPower(3000);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }
    
    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            registerEnterAbility(this::onEnterEff);
        }
        
        private void onEnterEff()
        {
            gainPower(getSIGNIOnField(getOpponent()), -1000, ChronoDuration.turnEnd());
        }
    }
}
