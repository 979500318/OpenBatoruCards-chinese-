package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.CardConst.UseTiming;
import open.batoru.data.Cost;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;

public final class PIECE_X_WISHIN extends Card {

    public PIECE_X_WISHIN()
    {
        setImageSets("WXDi-P11-004");

        setOriginalName("WISH IN…");
        setAltNames("ウィッシュイン Uisshu In");
        setDescription("jp",
                "このゲームの間、あなたは以下の能力を得る。" +
                "@>U：あなたのメインフェイズ開始時、あなたの手札が５枚以下の場合、カードを１枚引く。"
        );

        setName("en", "Wish In...");
        setDescription("en",
                "You gain the following ability for the duration of the game.\n" +
                "@>@U: At the beginning of your main phase, if you have five or fewer cards in your hand, draw a card."
        );
        
        setName("en_fan", "WISH IN...");
        setDescription("en_fan",
                "During this game, you gain the following ability:" +
                "@>@U: At the beginning of your main phase, if there are 5 or less cards in your hand, draw 1 card."
        );

		setName("zh_simplified", "WISH IN…");
        setDescription("zh_simplified", 
                "这场游戏期间，你得到以下的能力。\n" +
                "@>@U 你的主要阶段开始时，你的手牌在5张以下的场合，抽1张牌。@@\n"
        );

        setType(CardType.PIECE);
        setCost(Cost.colorless(1));
        setUseTiming(UseTiming.MAIN);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            registerPieceAbility(this::onPieceEff);
        }

        private void onPieceEff()
        {
            AutoAbility attachedAuto = new AutoAbility(GameEventId.PHASE_START, this::onAttachedAutoEff);
            attachedAuto.setCondition(this::onAttachedAutoEffCond);

            attachPlayerAbility(getOwner(), attachedAuto, ChronoDuration.permanent());
        }
        private ConditionState onAttachedAutoEffCond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.MAIN ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAttachedAutoEff(CardIndex caller)
        {
            if(getHandCount(getOwner()) <= 5)
            {
                draw(1);
            }
        }
    }
}
