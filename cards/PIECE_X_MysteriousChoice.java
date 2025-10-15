package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.data.DataTable;
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

public final class PIECE_X_MysteriousChoice extends Card {

    public PIECE_X_MysteriousChoice()
    {
        setImageSets("WXDi-P11-002");

        setOriginalName("ミステリアス・チョイス");
        setAltNames("ミステリアスチョイス Misuteriasu Choisu");
        setDescription("jp",
                "=U =E 合計３種類以上の色を持つ\n\n" +
                "このゲームの間、対戦相手は以下の能力を得る。" +
                "@>U：あなたのターン終了時、以下の３つからまだ選んでいないもの１つを選ぶ。\n" +
                "$$1あなたは手札を２枚捨てる。\n" +
                "$$2あなたのエナゾーンからカード２枚を選びトラッシュに置く。\n" +
                "$$3あなたは自分のシグニ１体を選びトラッシュに置く。"
        );
        
        setName("en", "Mysterious Choice");
        setDescription("en",
                "=U =E You have the three LRIG on your field with three or more different colors among all members.\n\n" +
                "Your opponent gains the following ability for the duration of the game." +
                "@>@U: At the end of your turn, choose one of the following that have not yet been chosen.\n" +
                "$$1 Discard two cards.\n" +
                "$$2 Choose two cards from your Ener Zone and put them into your trash.\n" +
                "$$3 Choose a SIGNI on your field and put it into its owner's trash."
        );
        
        setName("en_fan", "Mysterious Choice");
        setDescription("en_fan",
                "=U =E with 3 or more colors among them\n\n" +
                "During this game, your opponent gains the following ability:" +
                "@>@U: At the end of your turn, @[@|choose 1 of the following 3 that you haven't yet chosen:|@]@\n" +
                "$$1 Discard 2 cards from your hand.\n" +
                "$$2 Choose 2 cards from your ener zone, and put them into the trash.\n" +
                "$$3 Choose 1 of your SIGNI, and put it into the trash."
        );

		setName("zh_simplified", "诡秘·选项");
        setDescription("zh_simplified", 
                "=U=E持有合计3种类以上的颜色（你的场上的分身3只把这个条件满足）\n" +
                "这场游戏期间，对战对手得到以下的能力。\n" +
                "@>@U :你的回合结束时，从以下的3种选没有选过的1种。\n" +
                "$$1 你把手牌2张舍弃。\n" +
                "$$2 从你的能量区选2张牌放置到废弃区。\n" +
                "$$3 你选自己的精灵1只放置到废弃区。@@\n"
        );

        setType(CardType.PIECE);
        setLRIGTeam(CardLRIGTeam.DREAM_TEAM);
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
            
            attachPlayerAbility(getOpponent(), attachedAuto, ChronoDuration.permanent());
        }
        private ConditionState onAttachedAutoEffCond()
        {
            return !isOwnTurn() && getCurrentPhase() == GamePhase.END ? ConditionState.OK : ConditionState.BAD;
        }
        private int cacheChosen;
        private void onAttachedAutoEff(CardIndex caller)
        {
            if(cacheChosen == (1 | 1<<1 | 1<<2)) return;
            
            int modes = playerChoiceMode(getOpponent(), cacheChosen);
            cacheChosen |= modes;
            
            if((modes & 1) != 0)
            {
                discard(getOpponent(), 2);
            } else if((modes & 1<<1) != 0)
            {
                DataTable<CardIndex> data = playerTargetCard(getOpponent(), Math.min(2, getEnerCount(getOpponent())), new TargetFilter(TargetHint.BURN).own().fromEner());
                trash(data);
            } else if((modes & 1<<2) != 0)
            {
                CardIndex cardIndex = playerTargetCard(getOpponent(), new TargetFilter(TargetHint.TRASH).own().SIGNI()).get();
                trash(cardIndex);
            }
        }
    }
}
