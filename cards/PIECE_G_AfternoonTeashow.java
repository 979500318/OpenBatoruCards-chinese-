package open.batoru.data.cards;

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
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.CardAbilities;
import open.batoru.data.ability.PieceAbility;

public final class PIECE_G_AfternoonTeashow extends Card {

    public PIECE_G_AfternoonTeashow()
    {
        setImageSets("WXDi-P10-002");

        setOriginalName("アフタヌーンティーショー");
        setAltNames("Afutanuun Tii Shoo");
        setDescription("jp",
                "=U =E 合計３種類以上の色を持つ\n\n" +
                "このゲームの間、あなたは以下の能力を得る。" +
                "@>@U：あなたのアタックフェイズ開始時、以下の２つから１つを選ぶ。\n" +
                "$$1あなたのエナゾーンからあなたのセンタールリグと共通する色を持つシグニ１枚を対象とし、それを手札に加える。\n" +
                "$$2[[エナチャージ１]]"
        );

        setName("en", "Afternoon Tea Show");
        setDescription("en",
                "=U =E You have the three LRIG on your field with three or more different colors among all members.\n\n" +
                "You gain the following ability for the duration of the game.\n" +
                "@>@U: At the beginning of your attack phase, choose one of the following.\n" +
                "$$1 Add target SIGNI that shares a color with your Center LRIG from your Ener Zone to your hand.\n" +
                "$$2 [[Ener Charge 1]]."
        );
        
        setName("en_fan", "Afternoon Teashow");
        setDescription("en_fan",
                "=U =E with 3 or more colors among them\n\n" +
                "During this game, you gain the following ability:" +
                "@>@U: At the beginning of your attack phase, @[@|choose 1 of the following:|@]@\n" +
                "$$1 Target 1 SIGNI that shares a common color with your center LRIG from your ener zone, and add it to your hand.\n" +
                "$$2 [[Ener Charge 1]]"
        );

		setName("zh_simplified", "下午茶宴会");
        setDescription("zh_simplified", 
                "=U=E持有合计3种类以上的颜色（你的场上的分身3只把这个条件满足）\n" +
                "这场游戏期间，你得到以下的能力。\n" +
                "@>@U :你的攻击阶段开始时，从以下的2种选1种。\n" +
                "$$1 从你的能量区把持有与你的核心分身共通颜色的精灵1张作为对象，将其加入手牌。\n" +
                "$$2 [[能量填充1]]@@\n"
        );

        setType(CardType.PIECE);
        setLRIGTeam(CardLRIGTeam.DREAM_TEAM);
        setColor(CardColor.GREEN);
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
            return getColorsCount(getLRIGs(getOwner())) >= 3 ? ConditionState.OK : ConditionState.BAD;
        }
        private void onPieceEff()
        {
            AutoAbility attachedAuto = new AutoAbility(GameEventId.PHASE_START, this::onAttachedAutoEff);
            attachedAuto.setCondition(this::onAttachedAutoEffCond);
            
            attachPlayerAbility(getOwner(), attachedAuto, ChronoDuration.permanent());
        }
        private ConditionState onAttachedAutoEffCond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.ATTACK_PRE ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAttachedAutoEff(CardIndex caller)
        {
            if(playerChoiceMode() == 1)
            {
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.HAND).own().SIGNI().withColor(getLRIG(getOwner()).getIndexedInstance().getColor()).fromEner()).get();
                addToHand(target);
            } else {
                enerCharge(1);
            }
        }
    }
}
