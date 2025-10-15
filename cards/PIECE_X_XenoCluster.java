package open.batoru.data.cards;

import open.batoru.core.gameplay.GameConst.CardLocation;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameLog;
import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.CardConst.UseTiming;
import open.batoru.data.ability.PieceAbility;

public final class PIECE_X_XenoCluster extends Card {
    
    public PIECE_X_XenoCluster()
    {
        setImageSets("WXDi-P02-005", "WXDi-CP01-055", "PR-Di012");
        
        setOriginalName("ゼノ・クラスタ");
        setAltNames("ゼノクラスタ Zeno Kurasuta");
        setDescription("jp",
                "以下の２つから１つを選ぶ。\n" +
                "$$1カードを１枚引く。このターンに対戦相手の効果によってあなたの手札からカードが１枚以上トラッシュに移動していた場合、代わりにカードを３枚引く。\n" +
                "$$2[[エナチャージ１]]をする。このターンに対戦相手の効果によってあなたのエナゾーンからカードが１枚以上トラッシュに移動していた場合、代わりに[[エナチャージ３]]をする。"
        );
        
        setName("en", "Zeno Cluster");
        setDescription("en",
                "Choose one of the following.\n" +
                "$$1 Draw a card. If one or more cards were moved from your hand into your trash by your opponent's effect this turn, instead draw three cards.\n" +
                "$$2 [[Ener Charge 1]]. If one or more cards were moved from your Ener Zone into your trash by your opponent's effect this turn, instead [[Ener Charge 3]]."
        );
        
        setName("en_fan", "Xeno Cluster");
        setDescription("en_fan",
                "@[@|Choose 1 of the following:|@]@\n" +
                "$$1 Draw 1 card. If 1 or more cards were moved from your hand to the trash by an opponent's effect this turn, draw 3 cards instead.\n" +
                "$$2 [[Ener Charge 1]]. If 1 or more cards were moved from your ener zone to the trash by an opponent's effect this turn, [[Ener Charge 3]] instead."
        );
        
		setName("zh_simplified", "杰诺·集群");
        setDescription("zh_simplified", 
                "从以下的2种选1种。\n" +
                "$$1 抽1张牌。这个回合因为对战对手的效果从你的手牌把牌1张以上往废弃区移动过的场合，作为替代，抽3张牌。\n" +
                "$$2 [[能量填充1]]。这个回合因为对战对手的效果从你的能量区把牌1张以上往废弃区移动过的场合，作为替代，[[能量填充3]]。\n"
        );

        setType(CardType.PIECE);
        setUseTiming(UseTiming.ATTACK);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
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
            piece.setModeChoice(1);
        }
        
        private void onPieceEff()
        {
            if(piece.getChosenModes() == 1)
            {
                draw(GameLog.getTurnRecordsCount(event ->
                    event.getId() == GameEventId.DISCARD && event.getSourceAbility() != null && !isOwnCard(event.getSource()) && isOwnCard(event.getCaller())
                ) > 0 ? 3 : 1);
            } else {
                enerCharge(GameLog.getTurnRecordsCount(event ->
                    event.getId() == GameEventId.TRASH && event.getSourceAbility() != null && !isOwnCard(event.getSource()) &&
                    event.getCaller().getLocation() == CardLocation.ENER && isOwnCard(event.getCaller())
                ) > 0 ? 3 : 1);
            }
        }
    }
}
