package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.Cost;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.cost.TrashCost;

public final class KEY_R_RilMemoryOfTwinAttacks extends Card {

    public KEY_R_RilMemoryOfTwinAttacks()
    {
        setImageSets("PR-K011", "WDK12-009");

        setOriginalName("双撃の記憶　リル");
        setAltNames("ソウゲキノキオクリル Sougeki no Kioku Riru");
        setDescription("jp",
                "@U：あなたのメインフェイズ開始時、あなたのデッキの一番上を公開する。それがあなたのセンタールリグと共通する色を持つシグニの場合、それを手札に加える。\n" +
                "@A @[このキーを場からルリグトラッシュに置く]@：#C #Cを得る。"
        );

        setName("en", "Ril, Memory of Twin Attacks");
        setDescription("en",
                "@U: At the beginning of your main phase, reveal the top card of your deck. If it is a SIGNI that shares a common color with your center LRIG, add it to your hand.\n" +
                "@A @[Put this key from the field into the LRIG trash]@: Gain #C #C."
        );

		setName("zh_simplified", "双击的记忆 莉露");
        setDescription("zh_simplified", 
                "@U :你的主要阶段开始时，你的牌组最上面公开。其是持有与你的核心分身共通颜色的精灵的场合，将其加入手牌。\n" +
                "@A 这张钥匙从场上放置到分身废弃区:得到#C #C。\n"
        );

        setType(CardType.KEY);
        setColor(CardColor.RED);
        setCost(Cost.coin(1));

        setPlayFormat(PlayFormat.KEY);
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

            registerActionAbility(new TrashCost(), this::onActionEff);
        }

        private ConditionState onAutoEffCond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.MAIN ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            CardIndex cardIndex = reveal();
            
            if(cardIndex == null || !CardType.isSIGNI(cardIndex.getIndexedInstance().getTypeByRef()) ||
               !getLRIG(getOwner()).getIndexedInstance().getColor().matches(cardIndex.getIndexedInstance().getColor()) ||
               !addToHand(cardIndex))
            {
                returnToDeck(cardIndex, DeckPosition.TOP);
            }
        }

        private void onActionEff()
        {
            gainCoins(2);
        }
    }
}
