package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.cost.DownCost;

public final class SIGNI_K1_ForasWickedDevil extends Card {

    public SIGNI_K1_ForasWickedDevil()
    {
        setImageSets("WX24-P1-079");

        setOriginalName("凶魔　フォラス");
        setAltNames("キョウマフォラス Kyouma Forasu");
        setDescription("jp",
                "@U：このシグニがコストか効果によって場からトラッシュに置かれたとき、あなたのトラッシュから＜悪魔＞のシグニ１枚を対象とし、%Kを支払ってもよい。そうした場合、それを手札に加える。\n" +
                "@A #D：あなたのデッキの上からカードを３枚トラッシュに置く。"
        );

        setName("en", "Foras, Wicked Devil");
        setDescription("en",
                "@U: When this SIGNI is put from the field into the trash by a cost or an effect, target 1 <<Devil>> SIGNI from your trash, and you may pay %K. If you do, add it to your hand.\n" +
                "@A #D: Put the top 3 cards of your deck into the trash."
        );

		setName("zh_simplified", "凶魔 佛拉士");
        setDescription("zh_simplified", 
                "@U :当这只精灵因为费用或效果从场上放置到废弃区时，从你的废弃区把<<悪魔>>精灵1张作为对象，可以支付%K。这样做的场合，将其加入手牌。\n" +
                "@A 横置:从你的牌组上面把3张牌放置到废弃区。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.BLACK);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.DEVIL);
        setLevel(1);
        setPower(3000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
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

            registerActionAbility(new DownCost(), this::onActionEff);
        }
        
        private ConditionState onAutoEffCond(CardIndex cardIndex)
        {
            return getEvent().getSourceAbility() != null && cardIndex.isSIGNIOnField() ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.HAND).own().SIGNI().withClass(CardSIGNIClass.DEVIL).fromTrash()).get();
            
            if(target != null && payEner(Cost.color(CardColor.BLACK, 1)))
            {
                addToHand(target);
            }
        }

        private void onActionEff()
        {
            millDeck(3);
        }
    }
}
