package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.data.DataTable;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;

public final class SIGNI_R3_DracoNaturalStarPrincess extends Card {

    public SIGNI_R3_DracoNaturalStarPrincess()
    {
        setImageSets("WXDi-P05-035");

        setOriginalName("羅星姫　ドラコ");
        setAltNames("ラセイキドラコ Raseiki Doraco");
        setDescription("jp",
                "@U：あなたのアタックフェイズ開始時、あなたのデッキの上からカードを５枚公開し、それらのカードをシャッフルしてデッキの一番下に置く。その後、%X %X %X %X %X %Xからこの方法で公開されたレベル１のシグニ１枚につき%Xを減らしたエナコストを支払ってもよい。そうした場合、対戦相手のパワー12000以下のシグニ１体を対象とし、それをバニッシュする。"
        );

        setName("en", "Draco, Natural Planet Queen");
        setDescription("en",
                "@U: At the beginning of your attack phase, reveal the top five cards of your deck and put them on the bottom of your deck in a random order. Then, you may pay %X %X %X %X %X %X. Decrease this Ener cost by %X for every level one SIGNI revealed this way. If you do, vanish target SIGNI on your opponent's field with power 12000 or less."
        );
        
        setName("en_fan", "Draco, Natural Star Princess");
        setDescription("en_fan",
                "@U: At the beginning of your attack phase, reveal the top 5 cards of your deck, and shuffle them and put them on the bottom of your deck. Then, you may pay %X %X %X %X %X %X, with this ener cost being reduced by %X for each level 1 SIGNI revealed this way. If you do, target 1 of your opponent's SIGNI with power 12000 or less, and banish it."
        );

		setName("zh_simplified", "罗星姬 天龙座");
        setDescription("zh_simplified", 
                "@U 你的攻击阶段开始时，从你的牌组上面把5张牌公开，这些牌洗切放置到牌组最下面。然后，可以支付从%X %X %X %X %X %X中依据这个方法公开的等级1的精灵的数量，每有1张就减%X:的能量费用。这样做的场合，对战对手的力量12000以下的精灵1只作为对象，将其破坏。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.RED);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.SPACE);
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
        }

        private ConditionState onAutoEffCond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.ATTACK_PRE ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            reveal(5);
            
            int countLevel1 = new TargetFilter().own().SIGNI().withLevel(1).fromRevealed().getValidTargetsCount();
            
            int countRevealed = getRevealedCount();
            if(countRevealed > 0)
            {
                forEachCardInRevealed((CardIndex cardIndex) -> {
                    returnToDeck(cardIndex, DeckPosition.BOTTOM);
                });
                shuffleDeck(countRevealed, DeckPosition.BOTTOM);
            }
            
            if(payEner(Cost.colorless(6 - countLevel1)))
            {
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().withPower(0,12000)).get();
                banish(target);
            }
        }
    }
}
