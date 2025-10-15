package open.batoru.data.cards;

import open.batoru.core.gameplay.GameConst.CardStateFlag;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameLog;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.PieceAbility;

public final class PIECE_K_SeleneGirlsAcademyMoonNight extends Card {

    public PIECE_K_SeleneGirlsAcademyMoonNight()
    {
        setImageSets("WXDi-CP01-002");

        setOriginalName("世怜音女学院 Moon Night");
        setAltNames("セレイネジョガクインムーンライトナイト Sereine Jogakuin Muun Raito Naito Moon Light");
        setDescription("jp",
                "=U =E 黒のルリグを１体以上含む\n" +
                "=U このゲームの間にあなたがリレーピースを使用している\n\n" +
                "あなたのセンタールリグがレベル３以上の場合、対戦相手のデッキの上からカードを２４３４枚トラッシュに置く。"
        );

        setName("en", "SELENE Girls' Academy, Moon Night");
        setDescription("en",
                "=U =E You have one or more black LRIG on your team.\n=U You have used a Relay PIECE during this game.\n\n\nIf your Center LRIG is level three or more, put the top 2434 cards of your opponent's deck into their trash."
        );
        
        setName("en_fan", "Selene Girls' Academy Moon Night");
        setDescription("en_fan",
                "=U =E with 1 or more being black\n" +
                "=U You have used a Relay piece this game\n\n" +
                "If your center LRIG is level 3 or higher, put the top 2434 cards of your opponent's deck into the trash."
        );

		setName("zh_simplified", "世怜音女学院 Moon Night");
        setDescription("zh_simplified", 
                "=U=E含有黑色的分身1只以上\n" +
                "=U这场游戏期间你把中继和音使用过\n" +
                "你的核心分身在等级3以上的场合，从对战对手的牌组上面把2434张牌放置到废弃区。\n"
        );

        setType(CardType.PIECE);
        setLRIGTeam(CardLRIGTeam.DREAM_TEAM);
        setColor(CardColor.BLACK);
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
            if(new TargetFilter().own().anyLRIG().withColor(CardColor.BLACK).getValidTargetsCount() == 0 ||
               GameLog.getGameRecordsCount(e -> e.getId() == GameEventId.USE_PIECE && isOwnCard(e.getCaller()) && (e.getCaller().getCardStateFlags().getValue() & CardStateFlag.IS_RELAY) != 0) == 0) return ConditionState.BAD;
            
            return getLRIG(getOwner()).getIndexedInstance().getLevel().getValue() >= 3 ? ConditionState.OK : ConditionState.WARN;
        }
        private void onPieceEff()
        {
            if(getLRIG(getOwner()).getIndexedInstance().getLevel().getValue() >= 3)
            {
                millDeck(getOpponent(), 2434);
            }
        }
    }
}
