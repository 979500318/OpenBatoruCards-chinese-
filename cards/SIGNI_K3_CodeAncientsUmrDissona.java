package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.CardLocation;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.ActionAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.cost.DiscardCost;

public final class SIGNI_K3_CodeAncientsUmrDissona extends Card {

    public SIGNI_K3_CodeAncientsUmrDissona()
    {
        setImageSets("WXDi-P12-053", "WXDi-P12-053P");

        setOriginalName("コードアンシエンツ　ウムル//ディソナ");
        setAltNames("Koodo Anshientsu Umuru Disona");
        setDescription("jp",
                "@U：このシグニがアタックしたとき、あなたのトラッシュに#Sのカードが１０枚以上ある場合、対戦相手のシグニ１体を対象とし、%Kを支払ってもよい。そうした場合、ターン終了時まで、それのパワーを－10000する。\n\n" +
                "@A @[手札から#Sのカードを２枚捨てる]@：このカードをトラッシュから場に出す。"
        );

        setName("en", "Umr//Dissona, Code: Ancients");
        setDescription("en",
                "@U: Whenever this SIGNI attacks, if there are ten or more #S cards in your trash, you may pay %K. If you do, target SIGNI on your opponent's field gets --10000 power until end of turn.\n\n@A @[Discard two #S cards]@: Put this card from your trash onto your field. "
        );
        
        setName("en_fan", "Code Ancients Umr//Dissona");
        setDescription("en_fan",
                "@U: Whenever this SIGNI attacks, if there are 10 or more #S @[Dissona]@ cards in your trash, target 1 of your opponent's SIGNI, and you may pay %K. If you do, until end of turn, it gets --10000 power.\n\n" +
                "@A @[Discard 2 #S @[Dissona]@ cards from your hand]@: Put this card from your trash onto the field."
        );

		setName("zh_simplified", "古神代号 乌姆尔//失调");
        setDescription("zh_simplified", 
                "@U 当这只精灵攻击时，你的废弃区的#S的牌在10张以上的场合，对战对手的精灵1只作为对象，可以支付%K。这样做的场合，直到回合结束时为止，其的力量-10000。\n" +
                "@A 从手牌把#S的牌2张舍弃:这张牌从废弃区出场。（这个能力只有在这张牌在废弃区的场合才能使用。）\n"
        );

        setCardFlags(CardFlag.DISSONA);

        setType(CardType.SIGNI);
        setColor(CardColor.BLACK);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.ANCIENT_WEAPON);
        setLevel(3);
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
            
            registerAutoAbility(GameEventId.ATTACK, this::onAutoEff);

            ActionAbility act = registerActionAbility(new DiscardCost(2, new TargetFilter().dissona()), this::onActionEff);
            act.setCondition(this::onActionCondition);
            act.setActiveLocation(CardLocation.TRASH);
        }
        
        private void onAutoEff()
        {
            if(new TargetFilter().own().dissona().fromTrash().getValidTargetsCount() >= 10)
            {
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.MINUS).OP().SIGNI()).get();
                
                if(target != null && payEner(Cost.color(CardColor.BLACK, 1)))
                {
                    gainPower(target, -10000, ChronoDuration.turnEnd());
                }
            }
        }

        private ConditionState onActionCondition()
        {
            return isPlayable() ? ConditionState.OK : ConditionState.WARN;
        }
        private void onActionEff()
        {
            if(getCardIndex().getLocation() == CardLocation.TRASH)
            {
                putOnField(getCardIndex());
            }
        }
    }
}
