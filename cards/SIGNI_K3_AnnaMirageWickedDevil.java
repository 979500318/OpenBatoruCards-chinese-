package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.CardStateFlag;
import open.batoru.core.gameplay.GameConst.UseCondition;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityConst.ActionHint;
import open.batoru.data.ability.cost.EnerCost;

public final class SIGNI_K3_AnnaMirageWickedDevil extends Card {
    
    public SIGNI_K3_AnnaMirageWickedDevil()
    {
        setImageSets("WXDi-D06-017", "SPDi01-30");
        
        setOriginalName("凶魔　アンナ・ミラージュ");
        setAltNames("キョウマアンナミラージュ Kyouma Anna Miraaju");
        setDescription("jp",
                "=R あなたのシグニ１体の上に置く\n\n" +
                "@E %X：対戦相手のシグニ１体を対象とし、ターン終了時まで、それのパワーを－8000する。" +
                "~#：あなたのトラッシュから#Gを持たないシグニ１枚を対象とし、それを手札に加えるか場に出す。"
        );
        
        setName("en", "Anna Mirage, Doomed Evil");
        setDescription("en",
                "=R Place on top of a SIGNI on your field.\n\n" +
                "@E %X: Target SIGNI on your opponent's field gets --8000 power until end of turn." +
                "~#Add target SIGNI without a #G from your trash to your hand or put it onto your field."
        );
        
        setName("en_fan", "Anna Mirage, Wicked Devil");
        setDescription("en_fan",
                "=R Put on 1 of your SIGNI\n\n" +
                "@E %X: Target 1 of your opponent's SIGNI, and until end of turn, it gets --8000 power." +
                "~#Target 1 SIGNI without #G @[Guard]@ from your trash, and add it to your hand or put it onto the field."
        );
        
		setName("zh_simplified", "凶魔 安娜·蜃影");
        setDescription("zh_simplified", 
                "=R在你的精灵1只的上面放置（这个条件没有满足则不能出场）\n" +
                "@E %X:对战对手的精灵1只作为对象，直到回合结束时为止，其的力量-8000。" +
                "~#从你的废弃区把不持有#G的精灵1张作为对象，将其加入手牌或出场。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);
        
        setType(CardType.SIGNI);
        setColor(CardColor.BLACK);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.DEVIL);
        setLevel(3);
        setPower(13000);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
    
    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            setUseCondition(UseCondition.RISE, 1, new TargetFilter());
            
            registerEnterAbility(new EnerCost(Cost.colorless(1)), this::onEnterEff);
            
            registerLifeBurstAbility(this::onLifeBurstEff);
        }
        
        private void onEnterEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.MINUS).OP().SIGNI()).get();
            gainPower(target, -8000, ChronoDuration.turnEnd());
        }
        
        private void onLifeBurstEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter().own().SIGNI().not(new TargetFilter().withState(CardStateFlag.CAN_GUARD)).fromTrash()).get();
            
            if(target != null)
            {
                if(playerChoiceAction(ActionHint.HAND, ActionHint.FIELD) == 1)
                {
                    addToHand(target);
                } else {
                    putOnField(target);
                }
            }
        }
    }
}
