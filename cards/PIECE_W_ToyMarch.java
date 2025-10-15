package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.CardConst.UseTiming;
import open.batoru.data.CardConst.CardLRIGTeam;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityConst.AbilityGain;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.CardAbilities;
import open.batoru.data.ability.PieceAbility;

public final class PIECE_W_ToyMarch extends Card {

    public PIECE_W_ToyMarch()
    {
        setImageSets("WXDi-P10-001");

        setOriginalName("玩具行進曲");
        setAltNames("ガングコウシンキョク Gangekou Shinkyoku");
        setDescription("jp",
                "=U =E 合計３種類以上の色を持つ\n\n" +
                "あなたのセンタールリグがレベル３以上の場合、ターン終了時まで、あなたのすべてのシグニは@>@U：このシグニがアタックしたとき、このシグニをアップし、ターン終了時まで、このシグニは能力を失う。 @@を得る。"
        );

        setName("en", "March of the Toy Soldiers");
        setDescription("en",
                "=U =E You have the three LRIG on your field with three or more different colors among all members.\n\n" +
                "If your Center LRIG is level three or more, all SIGNI on your field gain@>@U: Whenever this SIGNI attacks, up it and it loses its abilities until end of turn.@@until end of turn."
        );
        
        setName("en_fan", "Toy March");
        setDescription("en_fan",
                "=U =E with 3 or more colors among them\n\n" +
                "If your center LRIG is level 3 or higher, until end of turn, all of your SIGNI gain:" +
                "@>@U: When this SIGNI attacks, up this SIGNI, and until end of turn, it loses its abilities."
        );

		setName("zh_simplified", "玩具行进曲");
        setDescription("zh_simplified", 
                "=U=E持有合计3种类以上的颜色（你的场上的分身3只把这个条件满足）\n" +
                "你的核心分身在等级3以上的场合，直到回合结束时为止，你的全部的精灵得到\n" +
                "@>@U :当这只精灵攻击时，这只精灵竖直，直到回合结束时为止，这只精灵的能力失去。@@\n"
        );

        setType(CardType.PIECE);
        setLRIGTeam(CardLRIGTeam.DREAM_TEAM);
        setColor(CardColor.WHITE);
        setCost(Cost.colorless(6));
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
            if(CardAbilities.getColorsCount(getLRIGs(getOwner())) < 3) return ConditionState.BAD;
            return getLRIG(getOwner()).getIndexedInstance().getLevel().getValue() >= 3 ? ConditionState.OK : ConditionState.WARN;
        }
        private void onPieceEff()
        {
            if(getLRIG(getOwner()).getIndexedInstance().getLevel().getValue() >= 3)
            {
                forEachSIGNIOnField(getOwner(), cardIndex -> {
                    AutoAbility attachedAuto = new AutoAbility(GameEventId.ATTACK, this::onAttachedAutoEff);
                    attachAbility(cardIndex, attachedAuto, ChronoDuration.turnEnd());
                });
            }
        }
        private void onAttachedAutoEff()
        {
            getEvent().getCallerCardIndex().getIndexedInstance().up();
            getEvent().getCallerCardIndex().getIndexedInstance().disableAllAbilities(getAbility().getSourceCardIndex(), AbilityGain.ALLOW, ChronoDuration.turnEnd());
        }
    }
}
