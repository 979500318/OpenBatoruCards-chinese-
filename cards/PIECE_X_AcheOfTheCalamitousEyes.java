package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.CardAbilities;
import open.batoru.data.ability.PieceAbility;
import open.batoru.game.FieldZone;

public final class PIECE_X_AcheOfTheCalamitousEyes extends Card {

    public PIECE_X_AcheOfTheCalamitousEyes()
    {
        setImageSets("WXDi-P09-003");

        setOriginalName("禍キ目ノ疼キ");
        setAltNames("マガツメノウズキ Magatsu Me no Uzuki");
        setDescription("jp",
                "=U =E 合計３種類以上の色を持つ\n\n" +
                "次の対戦相手のターン終了時まで、対戦相手のシグニゾーン１つを消す。"
        );

        setName("en", "Twinkling Eyes of Doom");
        setDescription("en",
                "=U =E You have the three LRIG on your field with three or more different colors among all members.\n\n" +
                "Erase one of your opponent's SIGNI Zones until the end of your opponent's next end phase. "
        );
        
        setName("en_fan", "Ache Of The Calamitous Eyes");
        setDescription("en_fan",
                "=U =E with 3 or more colors among them\n\n" +
                "Until the end of your opponent's next turn, delete 1 of your opponent's SIGNI zones."
        );

		setName("zh_simplified", "邪眼注视");
        setDescription("zh_simplified", 
                "=U=E持有合计3种类以上的颜色（你的场上的分身3只把这个条件满足）\n" +
                "直到下一个对战对手的回合结束时为止，对战对手的精灵区1个消除。（那里的全部放置到废弃区。玩家不能在那里把精灵配置）\n"
        );

        setType(CardType.PIECE);
        setLRIGTeam(CardLRIGTeam.DREAM_TEAM);
        setCost(Cost.colorless(5));
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
            return getColorsCount(getLRIGs(getOwner())) >= 3 ? ConditionState.OK : ConditionState.BAD;
        }
        private void onPieceEff()
        {
            FieldZone fieldZone = playerTargetZone(new TargetFilter(TargetHint.DELETE).OP().SIGNI()).get();
            deleteZone(fieldZone, ChronoDuration.nextTurnEnd(getOpponent()));
        }
    }
}
