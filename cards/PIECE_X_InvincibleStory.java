package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.PieceAbility;
import open.batoru.data.ability.stock.StockPlayerAbilityLRIGBarrier;

public final class PIECE_X_InvincibleStory extends Card {

    public PIECE_X_InvincibleStory()
    {
        setImageSets("WXDi-P16-TK01");
        setLinkedImageSets(Token_LRIGBarrier.IMAGE_SET);

        setOriginalName("インビンシブル・ストーリー");
        setAltNames("インビンシブルストーリー Inbinshiburu Sutoorii");
        setDescription("jp",
                "このピースはあなたの場にルリグが３体いなくても使用できる。\n\n" +
                "以下の３つから２つまで選ぶ。同じ選択肢を２回選んでもよい。\n" +
                "$$1%Wを支払ってもよい。そうした場合、【ルリグバリア】１つを得る。\n" +
                "$$2対戦相手のシグニ１体を対象とし、%Rを支払ってもよい。そうした場合、それをバニッシュする。\n" +
                "$$3%Bを支払ってもよい。そうした場合、カードを３枚引く。"
        );

        setName("en", "Invincible Story");
        setDescription("en",
                "This PIECE can be used even if three LRIG are not on your field.\n\nChoose up to two of the following. You may choose the same option more than once.\n$$1You may pay %W. If you do, gain a [[LRIG Barrier]].\n$$2You may pay %R. If you do, vanish target SIGNI on your opponent's field.\n$$3You may pay %B. If you do, draw three cards."
        );
        
        setName("en_fan", "Invincible Story");
        setDescription("en_fan",
                "You may use this piece even if there aren't 3 LRIG on your field\n\n" +
                "@[@|Choose up to 2 of the following:|@]@\n" +
                "((You may choose the same option twice))\n" +
                "$$1 You may pay %W. If you do, gain 1 [[LRIG Barrier]].\n" +
                "$$2 Target 1 of your opponent's SIGNI, and you may pay %R. If you do, banish it.\n" +
                "$$3 You may pay %B. If you do, draw 3 cards."
        );

		setName("zh_simplified", "无敌·篇章");
        setDescription("zh_simplified", 
                "这张和音即使你的场上的分身没有3只也能使用。\n" +
                "从以下的3种选2种最多。可以选相同选项2次。\n" +
                "$$1 可以支付%W。这样做的场合，得到[[分身屏障]]1个。\n" +
                "$$2 对战对手的精灵1只作为对象，可以支付%R。这样做的场合，将其破坏。\n" +
                "$$3 可以支付%B。这样做的场合，抽3张牌。\n"
        );

        setCardFlags(CardFlag.CRAFT);

        setType(CardType.PIECE);
        setColor(CardColor.RED, CardColor.WHITE, CardColor.BLUE);
        setCost(Cost.colorless(3));
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
            piece.setModeChoice(0,2);
            piece.setModeChoiceAllowRepeat(true);
            piece.setCanUseWithoutAssists(true);
        }
        
        private void onPieceEff()
        {
            int modes = piece.getChosenModes();
            if((modes & 1) != 0)
            {
                piece.callRepeatChoice(1, () -> {
                    if(payEner(Cost.color(CardColor.WHITE, 1)))
                    {
                        attachPlayerAbility(getOwner(), new StockPlayerAbilityLRIGBarrier(), ChronoDuration.permanent());
                    }
                });
            }
            if((modes & 1<<1) != 0)
            {
                piece.callRepeatChoice(1<<1, () -> {
                    CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI()).get();
                    
                    if(target != null && payEner(Cost.color(CardColor.RED, 1)))
                    {
                        banish(target);
                    }
                });
            }
            if((modes & 1<<2) != 0)
            {
                piece.callRepeatChoice(1<<2, () -> {
                    if(payEner(Cost.color(CardColor.BLUE, 1)))
                    {
                        draw(3);
                    }
                });
            }
        }
    }
}
