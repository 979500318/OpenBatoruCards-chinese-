package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.CardStateFlag;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.cost.DiscardCost;

public final class SIGNI_B1_CodeEatMatchaflaDissona extends Card {

    public SIGNI_B1_CodeEatMatchaflaDissona()
    {
        setImageSets("WXDi-P12-071");

        setOriginalName("コードイート　マチャフラ//ディソナ");
        setAltNames("コードイートマチャフラディソナ Koodo Iito Machafura Disona");
        setDescription("jp",
                "@U $T1：あなたが#Sのカードを１枚捨てたとき、対戦相手のシグニ１体を対象とし、それを凍結する。\n" +
               	"@E @[手札から#Sのカードを１枚捨てる]@：カードを１枚引く。" +
                "~#：対戦相手のシグニ１体を対象とし、それをダウンし凍結する。カードを１枚引く。"
        );

        setName("en", "Matcha Frappe//Dissona, Code: Eat");
        setDescription("en",
                "@U $T1: When you discard a #S card, freeze target SIGNI on your opponent's field.\n@E @[Discard a #S card]@: Draw a card." +
                "~#Down target SIGNI on your opponent's field and freeze it. Draw a card."
        );
        
        setName("en_fan", "Code Eat Matchafla//Dissona");
        setDescription("en_fan",
                "@U $T1: When you discard a #S @[Dissona]@ card, target 1 of your opponent's SIGNI, and freeze it.\n" +
                "@E @[Discard 1 #S @[Dissona]@ card from your hand]@: Draw 1 card." +
                "~#Target 1 of your opponent's SIGNI, and down and freeze it. Draw 1 card."
        );

		setName("zh_simplified", "食用代号 抹茶雪顶//失调");
        setDescription("zh_simplified", 
                "@U $T1 当你把#S的牌1张舍弃时，对战对手的精灵1只作为对象，将其冻结。\n" +
                "@E 从手牌把#S的牌1张舍弃:抽1张牌。" +
                "~#对战对手的精灵1只作为对象，将其#D并冻结。抽1张牌。\n"
        );

        setCardFlags(CardFlag.LIFEBURST | CardFlag.DISSONA);

        setType(CardType.SIGNI);
        setColor(CardColor.BLUE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.COOKING);
        setLevel(1);
        setPower(3000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            AutoAbility auto = registerAutoAbility(GameEventId.DISCARD, this::onAutoEff);
            auto.setCondition(this::onAutoEffCond);
            auto.setUseLimit(UseLimit.TURN, 1);
            
            registerEnterAbility(new DiscardCost(new TargetFilter().dissona()), this::onEnterEff);
            
            registerLifeBurstAbility(this::onLifeBurstEff);
        }
        
        private ConditionState onAutoEffCond(CardIndex caller)
        {
            return isOwnCard(caller) && caller.getIndexedInstance().isState(CardStateFlag.IS_DISSONA) ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.FREEZE).OP().SIGNI()).get();
            freeze(target);
        }
        
        private void onEnterEff()
        {
            draw(1);
        }
        
        private void onLifeBurstEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.FREEZE).OP().SIGNI()).get();
            down(target);
            freeze(target);
            
            draw(1);
        }
    }
}
