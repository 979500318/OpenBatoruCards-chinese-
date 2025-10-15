package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class SIGNI_K2_SabnockWickedAngel extends Card {
    
    public SIGNI_K2_SabnockWickedAngel()
    {
        setImageSets("WXDi-P03-084");
        
        setOriginalName("凶天　サブナック");
        setAltNames("キョウテンサブナック Kyouten Sabunakku");
        setDescription("jp",
                "~#：対戦相手のシグニ１体を対象とし、手札を１枚捨ててもよい。そうした場合、ターン終了時まで、それのパワーを－12000する。"
        );
        
        setName("en", "Sabnock, Doomed Angel");
        setDescription("en",
                "~#You may discard a card. If you do, target SIGNI on your opponent's field gets -12000 power until end of turn."
        );
        
        setName("en_fan", "Sabnock, Wicked Angel");
        setDescription("en_fan",
                "~#Target 1 of your opponent's SIGNI, and you may discard 1 card from your hand. If you do, until end of turn, it gets --12000 power."
        );
        
		setName("zh_simplified", "凶天 斯伯纳克");
        setDescription("zh_simplified", 
                "~#对战对手的精灵1只作为对象，可以把手牌1张舍弃。这样做的场合，直到回合结束时为止，其的力量-12000。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);
        
        setType(CardType.SIGNI);
        setColor(CardColor.BLACK);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.ANGEL);
        setLevel(2);
        setPower(10000);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
    
    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            registerLifeBurstAbility(this::onLifeBurstEff);
        }
        
        private void onLifeBurstEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.MINUS).OP().SIGNI()).get();
            
            if(target != null && discard(0,1).get() != null)
            {
                gainPower(target, -12000, ChronoDuration.turnEnd());
            }
        }
    }
}
