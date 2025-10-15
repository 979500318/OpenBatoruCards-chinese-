package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.CardLocation;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;

public final class SIGNI_R1_AritonCrimsonDevil extends Card {
    
    public SIGNI_R1_AritonCrimsonDevil()
    {
        setImageSets("WXDi-P06-052");
        
        setOriginalName("紅魔　アリトン");
        setAltNames("コウマアリトン Kouma Ariton");
        setDescription("jp",
                "@U：このシグニがコストか効果によって場からトラッシュに置かれたとき、対戦相手のパワー5000以下のシグニ１体を対象とし、%Xを支払ってもよい。そうした場合、それをバニッシュする。"
        );
        
        setName("en", "Ariton, Crimson Evil");
        setDescription("en",
                "@U: When this SIGNI is put into the trash from the field by a cost or an effect, you may pay %X. If you do, vanish target SIGNI on your opponent's field with power 5000 or less."
        );
        
        setName("en_fan", "Ariton, Crimson Devil");
        setDescription("en_fan",
                "@U: When this SIGNI is put from the field into the trash by a cost or effect, target 1 of your opponent's SIGNI with power 5000 or less, and you may pay %X. If you do, banish it."
        );
        
		setName("zh_simplified", "红魔 阿里通");
        setDescription("zh_simplified", 
                "@U :当这只精灵因为费用或效果从场上放置到废弃区时，对战对手的力量5000以下的精灵1只作为对象，可以支付%X。这样做的场合，将其破坏。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.RED);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.DEVIL);
        setLevel(1);
        setPower(2000);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
    
    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            AutoAbility auto = registerAutoAbility(GameEventId.TRASH, this::onAutoEff);
            auto.setCondition(this::onAutoEffCond);
        }
        
        private ConditionState onAutoEffCond(CardIndex caller)
        {
            return CardLocation.isSIGNI(caller.getLocation()) && getEvent().getSourceAbility() != null ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().withPower(0,5000)).get();
            
            if(target != null && payEner(Cost.colorless(1)))
            {
                banish(target);
            }
        }
    }
}
