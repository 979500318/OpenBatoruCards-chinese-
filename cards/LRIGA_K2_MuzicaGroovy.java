package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.cost.EnerCost;

public final class LRIGA_K2_MuzicaGroovy extends Card {
    
    public LRIGA_K2_MuzicaGroovy()
    {
        setImageSets("WXDi-P02-034");
        
        setOriginalName("ムジカ／／グルーヴィ");
        setAltNames("ムジカグルーヴィ Mujika Guruuvi");
        setDescription("jp",
                "@E：ターン終了時まで、対戦相手のすべてのシグニは@>@U：このシグニがバニッシュされたとき、あなたのデッキの上からこのシグニのレベル１につきカードを２枚トラッシュに置く。@@を得る。\n" +
                "@E %X %X %X：対戦相手のシグニ１体を対象とし、それをバニッシュする。その後、あなたのトラッシュからそれと同じレベルのシグニ１枚を対象とし、それを場に出す。"
        );
        
        setName("en", "Muzica//Groovy");
        setDescription("en",
                "@E: All SIGNI on your opponent's field gain@>@U: When this SIGNI is vanished, put the top two cards of your deck into your trash for each of its levels.@@until end of turn.\n" +
                "@E %X %X %X: Vanish target SIGNI on your opponent's field. Then, put target SIGNI from your trash that has the same level as the targeted SIGNI on your opponent's field onto your field."
        );
        
        setName("en_fan", "Muzica//Groovy");
        setDescription("en_fan",
                "@E: Until end of turn, all of your opponent's SIGNI gain:" +
                "@>@U: When this SIGNI is banished, put the top 2 cards of your deck into the trash for each of this SIGNI's levels.@@" +
                "@E %X %X %X: Target 1 of your opponent's SIGNI, and banish it. Then, target 1 SIGNI from your trash with the same level, and put it onto the field."
        );
        
		setName("zh_simplified", "穆希卡//魅力");
        setDescription("zh_simplified", 
                "@E :直到回合结束时为止，对战对手的全部的精灵得到\n" +
                "@>@U :当这只精灵被破坏时，从你的牌组上面依据这只精灵的等级的数量，每有1级就把2张牌放置到废弃区。@@\n" +
                "@E %X %X %X:对战对手的精灵1只作为对象，将其破坏。然后，从你的废弃区把与其相同等级的精灵1张作为对象，将其出场。\n"
        );

        setType(CardType.LRIG_ASSIST);
        setLRIGType(CardLRIGType.MUZICA);
        setLRIGTeam(CardLRIGTeam.DIAGRAM);
        setColor(CardColor.BLACK);
        setLevel(2);
        setLimit(+1);
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
            
            registerEnterAbility(this::onEnterEff1);
            registerEnterAbility(new EnerCost(Cost.colorless(3)), this::onEnterEff2);
        }
        
        private void onEnterEff1()
        {
            forEachSIGNIOnField(getOpponent(), cardIndex -> {
                AutoAbility attachedAuto = new AutoAbility(GameEventId.BANISH, this::onAttachedAutoEff);
                attachAbility(cardIndex, attachedAuto, ChronoDuration.turnEnd());
            });
        }
        private void onAttachedAutoEff()
        {
            getAbility().getSourceCardIndex().getIndexedInstance().millDeck(2 * getAbility().getSourceCardIndex().getIndexedInstance().getLevel().getValue());
        }
        
        private void onEnterEff2()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI()).get();
            
            if(target != null)
            {
                int level = target.getIndexedInstance().getLevel().getValue();
                
                banish(target);
                
                target = playerTargetCard(new TargetFilter(TargetHint.FIELD).own().SIGNI().withLevel(level).playable().fromTrash()).get();
                putOnField(target);
            }
        }
    }
}
