package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.CardDataColor;
import open.batoru.data.DataTable;
import open.batoru.data.ability.PieceAbility;

import java.util.HashSet;
import java.util.Set;

public final class PIECE_X_UNKNOWNMEMORY extends Card {

    public PIECE_X_UNKNOWNMEMORY()
    {
        setImageSets("WXDi-P12-005");

        setOriginalName("UNKNOWN MEMORY");
        setAltNames("アンノウンメモリー Announ Memorii");
        setDescription("jp",
                "以下の３つから１つを選ぶ。あなたの場にそれぞれ共通する色を持つルリグが２体以上いる場合、代わりに２つまで選ぶ。\n" +
                "$$1対戦相手のレベル２以下のシグニ１体を対象とし、それをバニッシュする。\n" +
                "$$2カードを２枚引く。\n" +
                "$$3【エナチャージ２】"
        );

        setName("en", "Unknown Memory");
        setDescription("en",
                "Choose one of the following. If you have two or more LRIG that share the same color on your field, instead choose up to two of the following.\n$$1 Vanish target level two or less SIGNI on your opponent's field.\n$$2 Draw two cards.\n$$3 [[Ener Charge 2]]."
        );
        
        setName("en_fan", "UNKNOWN MEMORY");
        setDescription("en_fan",
                "@[@|Choose 1 of the following:|@]@\n" +
                "((If there are 2 or more LRIGs on your field that share a common color, choose up to 2 instead.))\n" +
                "$$1 Target 1 of your opponent's level 2 or lower SIGNI, and banish it.\n" +
                "$$2 Draw 2 cards.\n" +
                "$$3 [[Ener Charge 2]]"
        );

		setName("zh_simplified", "UNKNOWN MEMORY");
        setDescription("zh_simplified", 
                "从以下的3种选1种。你的场上的持有共通颜色的分身在2只以上的场合，作为替代，选2种最多。\n" +
                "$$1 对战对手的等级2以下的精灵1只作为对象，将其破坏。\n" +
                "$$2 抽2张牌。\n" +
                "$$3 [[能量填充2]]\n"
        );

        setCardFlags(CardFlag.DISSONA);

        setType(CardType.PIECE);
        setUseTiming(UseTiming.MAIN);

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
            piece.setOnModesChosenPre(this::onPieceEffPreModeChoice);
            piece.setModeChoice(1);
        }
        
        private void onPieceEffPreModeChoice()
        {
            DataTable<CardIndex> data = getLRIGs(getOwner());
            if(data.size() < 2) return;
            
            Set<CardColor> cacheColors = new HashSet<>();
            for(int i=0;i<data.size();i++)
            {
                CardDataColor color = data.get(i).getIndexedInstance().getColor();
                if(color.matches(cacheColors))
                {
                    piece.setModeChoice(0,2);
                    break;
                }
                
                cacheColors.addAll(color.getValue());
            }
        }
        private void onPieceEff()
        {
            int modes = piece.getChosenModes();
            
            if((modes & 1) != 0)
            {
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().withLevel(0,2)).get();
                banish(target);
            }
            if((modes & 1<<1) != 0)
            {
                draw(2);
            }
            if((modes & 1<<2) != 0)
            {
                enerCharge(2);
            }
        }
    }
}
