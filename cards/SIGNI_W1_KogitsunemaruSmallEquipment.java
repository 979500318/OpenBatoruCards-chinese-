package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.modifiers.PowerModifier;

public final class SIGNI_W1_KogitsunemaruSmallEquipment extends Card {
    
    public SIGNI_W1_KogitsunemaruSmallEquipment()
    {
        setImageSets("WXDi-P02-048");
        
        setOriginalName("小装　コギツネマル");
        setAltNames("ショウソウコギツネマル Shousou Kogitsunemaru");
        setDescription("jp",
                "@C：あなたの場に他のシグニがあるかぎり、このシグニのパワーは＋4000される。" +
                "~#：対戦相手のパワー8000以下のシグニ１体を対象とし、%Wを支払ってもよい。そうした場合、それを手札に戻す。"
        );
        
        setName("en", "Kogitsunemaru, Lightly Armed");
        setDescription("en",
                "@C: As long as there is another SIGNI on your field, this SIGNI gets +4000 power." +
                "~#You may pay %W. If you do, return target SIGNI on your opponent's field with power 8000 or less to its owner's hand."
        );
        
        setName("en_fan", "Kogitsunemaru, Small Equipment");
        setDescription("en_fan",
                "@C: As long as there is another SIGNI on your field, this SIGNI gets +4000 power." +
                "~#Target 1 of your opponent's SIGNI with power 8000 or less, and you may pay %W. If you do, return it to their hand."
        );
        
		setName("zh_simplified", "小装 小狐丸宗近");
        setDescription("zh_simplified", 
                "@C :你的场上有其他的精灵时，这只精灵的力量+4000。" +
                "~#对战对手的力量8000以下的精灵1只作为对象，可以支付%W。这样做的场合，将其返回手牌。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);
        
        setType(CardType.SIGNI);
        setColor(CardColor.WHITE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.ARM);
        setLevel(1);
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
            
            registerConstantAbility(this::onConstEffCond, new PowerModifier(4000));
            
            registerLifeBurstAbility(this::onLifeBurstEff);
        }
        
        private ConditionState onConstEffCond()
        {
            return getSIGNICount(getOwner()) > 1 ? ConditionState.OK : ConditionState.BAD;
        }
        
        private void onLifeBurstEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.HAND).OP().SIGNI().withPower(0,8000)).get();
            
            if(target != null && payEner(Cost.color(CardColor.WHITE, 1)))
            {
                addToHand(target);
            }
        }
    }
}
