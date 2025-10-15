package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.data.DataTable;
import open.batoru.core.gameplay.GameConst.CardLocation;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.events.EventMove;

public final class SIGNI_R3_ShivaCrimsonDevilPrincess extends Card {
    
    public SIGNI_R3_ShivaCrimsonDevilPrincess()
    {
        setImageSets("WXDi-P06-035");
        
        setOriginalName("紅魔姫　シヴァ");
        setAltNames("コウマキシヴァ Koumaki Shiva");
        setDescription("jp",
                "@U：あなたのメインフェイズ以外でこのシグニが場を離れたとき、カードを１枚引く。\n" +
                "@E：手札をすべて捨てる。その後、この方法でカードを２枚捨てた場合、対戦相手のパワー10000以下のシグニ１体を対象とし、それをバニッシュする。３枚以上捨てた場合、代わりに対戦相手のシグニ１体を対象とし、それをバニッシュする。"
        );
        
        setName("en", "Shiva, Crimson Evil Queen");
        setDescription("en",
                "@U: When this SIGNI leaves the field outside of your main phase, draw a card.\n" +
                "@E: Discard your hand. Then, if you discarded two cards this way, vanish target SIGNI on your opponent's field with power 10000 or less. If you discarded three or more cards, instead vanish target SIGNI on your opponent's field."
        );
        
        setName("en_fan", "Shiva, Crimson Devil Princess");
        setDescription("en_fan",
                "@U: When this SIGNI leaves the field other than during your main phase, draw 1 card.\n" +
                "@E: Discard all cards from your hand. Then, if you discarded 2 cards this way, target 1 of your opponent's SIGNI with power 10000 or less, and banish it. If you discarded 3 or more, instead target 1 of your opponent's SIGNI and banish it."
        );
        
		setName("zh_simplified", "红魔姬 湿婆");
        setDescription("zh_simplified", 
                "@U :当在你的主要阶段以外把这只精灵离场时，抽1张牌。\n" +
                "@E :手牌全部舍弃。然后，这个方法把2张牌舍弃的场合，对战对手的力量10000以下的精灵1只作为对象，将其破坏。3张以上舍弃的场合，作为替代，对战对手的精灵1只作为对象，将其破坏。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.RED);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.DEVIL);
        setLevel(3);
        setPower(10000);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
    
    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            AutoAbility auto = registerAutoAbility(GameEventId.MOVE, this::onAutoEff);
            auto.setCondition(this::onAutoEffCond);
            
            registerEnterAbility(this::onEnterEff);
        }
        
        private ConditionState onAutoEffCond()
        {
            return (!isOwnTurn() || getCurrentPhase() != GamePhase.MAIN) && !CardLocation.isSIGNI(EventMove.getDataMoveLocation()) ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff()
        {
            draw(1);
        }
        
        private void onEnterEff()
        {
            DataTable<CardIndex> data = discard(getCardsInHand(getOwner()));
            
            if(data.size() >= 2)
            {
                TargetFilter filter = new TargetFilter(TargetHint.BANISH).OP().SIGNI();
                if(data.size() == 2) filter = filter.withPower(0,10000);
                
                CardIndex target = playerTargetCard(filter).get();
                banish(target);
            }
        }
    }
}
