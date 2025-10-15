package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.CardStateFlag;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.cost.CoinCost;
import open.batoru.data.ability.cost.EnerCost;

public final class SIGNI_B3_RememberMemoriaAzureWisdomPrincess extends Card {

    public SIGNI_B3_RememberMemoriaAzureWisdomPrincess()
    {
        setImageSets("WXDi-P11-043", "WXDi-P11-043P");

        setOriginalName("蒼英姫　リメンバ//メモリア");
        setAltNames("ソウエイキリメンバメモリア Soueiki Rimenba Memoria");
        setDescription("jp",
                "@U：あなたのアタックフェイズ開始時、数字１つを宣言する。あなたのデッキの一番上のカードを公開し、そのカードが宣言した数字と同じレベルのシグニの場合、そのカードを手札に加える。\n" +
                "@E %B：対戦相手のレベル２以下の凍結状態のシグニ１体を対象とし、それを手札に戻す。\n" +
                "@E #C：対戦相手のシグニ１体を対象とし、それを凍結する。"
        );

        setName("en", "Remember//Memoria, Azure Mind Queen");
        setDescription("en",
                "@U: At the beginning of your attack phase, declare a number. Reveal the top card of your deck. If that card is a SIGNI with the same level as the declared number, add that card to your hand.\n" +
                "@E %B: Return target level two or less frozen SIGNI on your opponent's field to its owner's hand.\n" +
                "@E #C: Freeze target SIGNI on your opponent's field."
        );
        
        setName("en_fan", "Remember//Memoria, Azure Wisdom Princess");
        setDescription("en_fan",
                "@U: At the beginning of your attack phase, declare 1 number. Reveal the top card of your deck, and if that card is a SIGNI with the same level as the declared number, add it to your hand.\n" +
                "@E %B: Target 1 of your opponent's level 2 or lower frozen SIGNI, and return it to their hand.\n" +
                "@E #C: Target 1 of your opponent's SIGNI, and freeze it."
        );

		setName("zh_simplified", "苍英姬 忆//回忆");
        setDescription("zh_simplified", 
                "@U :你的攻击阶段开始时，数字1种宣言。你的牌组最上面的牌公开，那张牌是与宣言数字相同等级的精灵的场合，那张牌加入手牌。\n" +
                "@E %B:对战对手的等级2以下的冻结状态的精灵1只作为对象，将其返回手牌。\n" +
                "@E #C:对战对手的精灵1只作为对象，将其冻结。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.BLUE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.WISDOM);
        setLevel(3);
        setPower(12000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            AutoAbility auto = registerAutoAbility(GameEventId.PHASE_START, this::onAutoEff);
            auto.setCondition(this::onAutoEffCond);
            
            registerEnterAbility(new EnerCost(Cost.color(CardColor.BLUE, 1)), this::onEnterEff1);
            registerEnterAbility(new CoinCost(1), this::onEnterEff2);
        }

        private ConditionState onAutoEffCond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.ATTACK_PRE ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            int number = playerChoiceNumber(0,1,2,3,4,5) - 1;
            
            CardIndex cardIndex = reveal();
            
            if(cardIndex == null ||
               !CardType.isSIGNI(cardIndex.getIndexedInstance().getTypeByRef()) || cardIndex.getIndexedInstance().getLevelByRef() != number ||
               !addToHand(cardIndex))
            {
                returnToDeck(cardIndex, DeckPosition.TOP);
            }
        }
        
        private void onEnterEff1()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.HAND).OP().SIGNI().withLevel(0,2).withState(CardStateFlag.FROZEN)).get();
            addToHand(target);
        }
        
        private void onEnterEff2()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.FREEZE).OP().SIGNI()).get();
            freeze(target);
        }
    }
}
