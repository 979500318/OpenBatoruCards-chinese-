package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.data.DataTable;
import open.batoru.core.gameplay.GameConst.CardStateFlag;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameLog;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.PieceAbility;

public final class PIECE_W_LazuLightFLASH extends Card {

    public PIECE_W_LazuLightFLASH()
    {
        setImageSets("WXDi-CP01-004");

        setOriginalName("LazuLight FLASH");
        setAltNames("ラズライトフラッシュ Razuraito Furasshu");
        setDescription("jp",
                "=U =E 白のルリグを１体以上含む\n" +
                "=U このゲームの間にあなたがリレーピースを使用している\n\n" +
                "以下の３つから２つまで選ぶ。\n" +
                "$$1コラボライバー２人を呼ぶ。\n" +
                "$$2対戦相手のシグニ１体を対象とし、それを手札に戻す。\n" +
                "$$3あなたのデッキの上からカードを３枚見る。その中からカードを好きな枚数手札に加え、残りをエナゾーンに置く。"
        );

        setName("en", "LazuLight FLASH");
        setDescription("en",
                "=U =E You have one or more white LRIG on your team.\n=U You have used a Relay PIECE during this game.\n\nChoose up to two of the following.\n$$1 Invite two Collab Livers. \n$$2 Return target SIGNI on your opponent's field to its owner's hand.\n$$3 Look at the top three cards of your deck. Add any number of cards from among them to your hand and put the rest into your Ener Zone."
        );
        
        setName("en_fan", "LazuLight FLASH");
        setDescription("en_fan",
                "=U =E with one or more being white.\n" +
                "=U You have used a Relay piece this game.\n\n" +
                "@[@|Choose 2 of the following:|@]@\n" +
                "$$1 Invite 2 CollaboLivers.\n" +
                "$$2 Target 1 of your opponent's SIGNI, and return it to their hand.\n" +
                "$$3 Look at the top 3 cards of your deck. Add any number of them to your hand, and put the rest into the ener zone."
        );

		setName("zh_simplified", "LazuLight FLASH");
        setDescription("zh_simplified", 
                "=U=E含有白色的分身1只以上\n" +
                "=U这场游戏期间你把中继和音使用过\n" +
                "从以下的3种选2种最多。\n" +
                "$$1 呼唤联动主播2人。\n" +
                "$$2 对战对手的精灵1只作为对象，将其返回手牌。\n" +
                "$$3 从你的牌组上面看3张牌。从中把牌任意张数加入手牌，剩下的放置到能量区。\n"
        );

        setType(CardType.PIECE);
        setLRIGTeam(CardLRIGTeam.DREAM_TEAM);
        setColor(CardColor.WHITE);
        setUseTiming(UseTiming.MAIN);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        private final PieceAbility piece;
        public IndexedInstance(int cardId)
        {
            super(cardId);

            piece = registerPieceAbility(this::onPieceEff);
            piece.setCondition(this::onPieceEffCond);
            piece.setModeChoice(2);
        }

        private ConditionState onPieceEffCond()
        {
            return new TargetFilter().own().anyLRIG().withColor(CardColor.WHITE).getValidTargetsCount() > 0 &&
                    GameLog.getGameRecordsCount(e -> e.getId() == GameEventId.USE_PIECE && isOwnCard(e.getCaller()) && (e.getCaller().getCardStateFlags().getValue() & CardStateFlag.IS_RELAY) != 0) > 0 ?
                     ConditionState.OK : ConditionState.BAD;
        }
        private void onPieceEff()
        {
            int modes = piece.getChosenModes();
            
            if((modes & 1) != 0)
            {
                inviteCollaboLivers(2);
            }
            if((modes & 1<<1) != 0)
            {
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.HAND).OP().SIGNI()).get();
                addToHand(target);
            }
            if((modes & 1<<2) != 0)
            {
                look(3);
                
                DataTable<CardIndex> data = playerTargetCard(0,3, new TargetFilter(TargetHint.HAND).own().fromLooked());
                addToHand(data);
                
                putInEner(getCardsInLooked(getOwner()));
            }
        }
    }
}
