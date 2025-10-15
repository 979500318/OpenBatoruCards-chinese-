package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.CardConst.UseTiming;
import open.batoru.data.Cost;

public final class ARTS_K_BlindPunish extends Card {

    public ARTS_K_BlindPunish()
    {
        setImageSets("WXK01-033");

        setOriginalName("ブラインド・パニッシュ");
        setAltNames("ブラインドパニッシュ Buraindo Panisshu");
        setDescription("jp",
                "対戦相手のレベル３以下のシグニ１体を対象とし、それをバニッシュする。"
        );

        setName("en", "Blind Punish");
        setDescription("en",
                "Target 1 of your opponent's level 3 or lower SIGNI, and banish it."
        );

		setName("zh_simplified", "致盲·惩戒");
        setDescription("zh_simplified", 
                "对战对手的等级3以下的精灵1只作为对象，将其破坏。\n"
        );

        setType(CardType.ARTS);
        setColor(CardColor.BLACK);
        setCost(Cost.color(CardColor.BLACK, 1));
        setUseTiming(UseTiming.MAIN | UseTiming.ATTACK);

        setPlayFormat(PlayFormat.KEY);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            registerARTSAbility(this::onARTSEff);
        }
        
        private void onARTSEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().withLevel(0,3)).get();
            banish(target);
        }
    }
}
