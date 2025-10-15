package open.batoru.data.cards;

import open.batoru.core.Game;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.CutInResponse;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.PieceAbility;

public final class PIECE_BWK_EternalImmortalKyurukyurun extends Card {
    
    public PIECE_BWK_EternalImmortalKyurukyurun()
    {
        setImageSets("WXDi-P05-006");
        
        setOriginalName("永遠♡不滅　きゅるきゅる～ん☆");
        setAltNames("エイエンフメツキュルキュルン Eien Fumetsu Kyurukyurun Eternal Immortal");
        setDescription("jp",
                "=U =T ＜きゅるきゅるーん☆＞\n\n" +
                "このピースは、対戦相手が=U =Tを持つピースを使用する際、カットインして使用できる。\n\n" +
                "以下の２つから１つを選ぶ。\n" +
                "$$1=U =T を持つ対戦相手のピース１枚を対象とし、それの効果を打ち消す。この方法で打ち消されたピースはゲームから除外される。\n" +
                "$$2カードを１枚引き[[エナチャージ１]]をする。"
        );
        
        setName("en", "Eternal♡Immortal Kyurukyurun☆");
        setDescription("en",
                "=U =T <<Kyurukyurun☆>>\n" +
                "This PIECE can be used to cut-in just after your opponent uses a PIECE with =U =T.\n\n" +
                "Choose one of the following.\n" +
                "$$1 Counter the effects of target PIECE in your opponent's Check Zone with =U =T. The PIECE countered this way is removed from the game.\n" +
                "$$2 Draw a card and [[Ener Charge 1]]."
        );
        
        setName("en_fan", "Eternal♡Immortal Kyurukyurun☆");
        setDescription("en_fan",
                "=U =T <<Kyurukyurun☆>>\n\n" +
                "((This piece can be used as a cut-in when your opponent uses a piece with =U =T))\n\n" +
                "@[@|Choose 1 of the following:|@]@\n" +
                "$$1 Target 1 of your opponent's pieces with =U =T, and cancel its effects. Exclude the piece canceled this way from the game.\n" +
                "$$2 Draw 1 card and [[Ener Charge 1]]."
        );
        
		setName("zh_simplified", "永远♡不灭 萌萌～哒☆");
        setDescription("zh_simplified", 
                "=U=T<<きゅるきゅるーん☆>>\n" +
                "这张和音在，对战对手把持有=U=T的和音使用时，能切入使用。\n" +
                "从以下的2种选1种。\n" +
                "$$1 持有=U=T的对战对手的和音1张作为对象，将其的效果取消。这个方法取消的和音从游戏除外。\n" +
                "$$2 抽1张牌并[[能量填充1]]。\n"
        );

        setType(CardType.PIECE);
        setLRIGTeam(CardLRIGTeam.KYURUKYURUN);
        setColor(CardColor.BLUE, CardColor.WHITE, CardColor.BLACK);
        setUseTiming(UseTiming.MAIN | UseTiming.ATTACK);
        
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
            piece.setOnAbilityInit(() -> piece.getActiveUseTiming().addExternalValue(() -> {
                CutInResponse currentCutInResponse = Game.getCurrentGame().getGameRules().getEffectProcessor().getCutInResponse();
                return currentCutInResponse != null &&
                       currentCutInResponse.getCutInCardType() == CardType.PIECE &&
                       currentCutInResponse.getSourceCardIndex().getCardReference().getLRIGTeam() != null &&
                       currentCutInResponse.getSourceCardIndex().getCardReference().getLRIGTeam() != CardLRIGTeam.DREAM_TEAM ? UseTiming.SPELLCUTIN : 0;
            }));
            piece.setCutInExpectedCardType(CardType.PIECE);
        }
        
        private void onPieceEff()
        {
            if(piece.getChosenModes() == 1)
            {
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.CANCEL).OP().piece().withLRIGTeam().fromCheckZone()).get();
                cancel(target);
            } else {
                draw(1);
                enerCharge(1);
            }
        }
    }
}
