package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.CardConst.UseTiming;
import open.batoru.data.CardConst.CardLRIGTeam;
import open.batoru.data.Cost;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.CardAbilities;
import open.batoru.data.ability.PieceAbility;

public final class PIECE_X_TricksterStardom extends Card {

    public PIECE_X_TricksterStardom()
    {
        setImageSets("WXDi-P11-003");

        setOriginalName("トリックスター・スターダム！");
        setAltNames("トリックスタースターダム Torikkusutaa Sutaadamu");
        setDescription("jp",
                "=U =E 合計３種類以上の色を持つ\n\n" +
                "このゲームの間、あなたは以下の能力を得る。" +
                "@>U：あなたのメインフェイズ開始時、以下の３つからまだ選んでいないもの１つを選ぶ。\n" +
                "$$1カードを２枚引く。\n" +
                "$$2【エナチャージ２】\n" +
                "$$3あなたのトラッシュからシグニ１枚を対象とし、それをデッキの一番上に置く。"
        );

        setName("en", "Trickster Stardom!");
        setDescription("en",
                "=U =E You have the three LRIG on your field with three or more different colors among all members.\n\n" +
                "You gain the following ability for the duration of the game." +
                "@>@U: At the beginning of your main phase, choose one of the following that have not yet been chosen.\n" +
                "$$1 Draw two cards.\n" +
                "$$2 [[Ener Charge 2]].\n" +
                "$$3 Put target SIGNI from your trash on top of your deck."
        );
        
        setName("en_fan", "Trickster Stardom");
        setDescription("en_fan",
                "=U =E with 3 or more colors among them\n\n" +
                "During this game, you gain the following ability:" +
                "@>@U: At the beginning of your main phase, @[@|choose 1 of the following 3 that you haven't yet chosen:|@]@\n" +
                "$$1 Draw 2 cards.\n" +
                "$$2 [[Ener Charge 2]]\n" +
                "$$3 Target 1 SIGNI from your trash, and put it on the top of your deck."
        );

		setName("zh_simplified", "瞒天·过海！");
        setDescription("zh_simplified", 
                "=U=E持有合计3种类以上的颜色（你的场上的分身3只把这个条件满足）\n" +
                "这场游戏期间，你得到以下的能力。\n" +
                "@>@U :你的主要阶段开始时，从以下的3种选没有选过的1种。\n" +
                "$$1 抽2张牌。\n" +
                "$$2 [[能量填充2]]\n" +
                "$$3 从你的废弃区把精灵1张作为对象，将其放置到牌组最上面。@@\n"
        );

        setType(CardType.PIECE);
        setLRIGTeam(CardLRIGTeam.DREAM_TEAM);
        setCost(Cost.colorless(1));
        setUseTiming(UseTiming.MAIN);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            PieceAbility piece = registerPieceAbility(this::onPieceEff);
            piece.setCondition(this::onPieceEffCond);
        }

        private ConditionState onPieceEffCond()
        {
            return CardAbilities.getColorsCount(getLRIGs(getOwner())) >= 3 ? ConditionState.OK : ConditionState.BAD;
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
        private int cacheChosen;
        private void onAttachedAutoEff(CardIndex caller)
        {
            if(cacheChosen == (1 | 1<<1 | 1<<2)) return;
            
            int modes = playerChoiceMode(getOwner(), cacheChosen);
            cacheChosen |= modes;
            
            if((modes & 1) != 0)
            {
                draw(2);
            } else if((modes & 1<<1) != 0)
            {
                enerCharge(2);
            } else if((modes & 1<<2) != 0)
            {
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.TOP).own().SIGNI().fromTrash()).get();
                returnToDeck(target, DeckPosition.TOP);
            }
        }
    }
}
