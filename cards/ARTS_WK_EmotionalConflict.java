package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.CardConst.UseTiming;
import open.batoru.data.Cost;

public final class ARTS_WK_EmotionalConflict extends Card {

    public ARTS_WK_EmotionalConflict()
    {
        setImageSets("WX24-P4-003", "WX24-P4-003U");

        setOriginalName("エモーショナル・コンフリクト");
        setAltNames("エモーショナルコンフリクト Emooshonaru Konfurikuto");
        setDescription("jp",
                "対戦相手のシグニ１体を対象とし、それを手札に戻す。あなたのトラッシュからそれと同じパワーのシグニ１枚を対象とし、それを手札に加える。"
        );

        setName("en", "Emotional Conflict");
        setDescription("en",
                "Target 1 of your opponent's SIGNI, and return it to their hand. Target 1 SIGNI with the same power as that SIGNI from your trash, and add it to your hand."
        );

		setName("zh_simplified", "情感·冲突");
        setDescription("zh_simplified", 
                "对战对手的精灵1只作为对象，将其返回手牌。从你的废弃区把与其相同力量的精灵1张作为对象，将其加入手牌。\n"
        );

        setType(CardType.ARTS);
        setColor(CardColor.WHITE, CardColor.BLACK);
        setCost(Cost.color(CardColor.WHITE, 1) + Cost.color(CardColor.BLACK, 1));
        setUseTiming(UseTiming.ATTACK);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            registerARTSAbility(this::onARTSEff);
        }

        private void onARTSEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.HAND).OP().SIGNI()).get();
            double power = target.getIndexedInstance().getPower().getValue();
            addToHand(target);
            
            target = playerTargetCard(new TargetFilter(TargetHint.HAND).own().SIGNI().withPower(power).fromTrash()).get();
            addToHand(target);
        }
    }
}
