package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AbilityConst.AbilityGain;
import open.batoru.data.ability.stock.StockAbilityCantAttack;

public final class SIGNI_W2_LunarCruiserNaturalStar extends Card {

    public SIGNI_W2_LunarCruiserNaturalStar()
    {
        setImageSets("WX24-P3-063");

        setOriginalName("羅星　ルナカー");
        setAltNames("ラセイルナカー Rasei Runakaa");
        setDescription("jp",
                "@E：あなたのデッキの一番上を公開する。そのカードがシグニの場合、ターン終了時まで、そのシグニと同じレベルの対戦相手のすべてのシグニは能力を失う。" +
                "~#：対戦相手のルリグ１体を対象とし、ターン終了時まで、それは@>@C@#：アタックできない。@@@@を得る。"
        );

        setName("en", "Lunar Cruiser, Natural Star");
        setDescription("en",
                "@E: Reveal the top card of your deck. If it is a SIGNI, until end of turn, all of your opponent's SIGNI with the same level as that SIGNI lose their abilities.\n" +
                "~#Target 1 of your opponent's LRIG, and until end of turn, it gains:@>@C@#: Can't attack."
        );

		setName("zh_simplified", "罗星 月球巡洋舰");
        setDescription("zh_simplified", 
                "@E :你的牌组最上面公开。那张牌是精灵的场合，直到回合结束时为止，与那张精灵相同等级的对战对手的全部的精灵的能力失去。" +
                "~#对战对手的分身1只作为对象，直到回合结束时为止，其得到\n" +
                "@>@C :不能攻击。@@\n"
        );

        setCardFlags(CardFlag.LIFEBURST);

        setType(CardType.SIGNI);
        setColor(CardColor.WHITE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.SPACE);
        setLevel(2);
        setPower(8000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            registerEnterAbility(this::onEnterEff);
            
            registerLifeBurstAbility(this::onLifeBurstEff);
        }
        
        private void onEnterEff()
        {
            CardIndex cardIndex = reveal();
            
            if(cardIndex != null)
            {
                returnToDeck(cardIndex, DeckPosition.TOP);
                
                if(CardType.isSIGNI(cardIndex.getIndexedInstance().getTypeByRef()))
                {
                    int level = cardIndex.getIndexedInstance().getLevelByRef();
                    
                    disableAllAbilities(new TargetFilter().OP().SIGNI().withLevel(level).getExportedData(), AbilityGain.ALLOW, ChronoDuration.turnEnd());
                }
            }
        }
        
        private void onLifeBurstEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.ABILITY).OP().anyLRIG()).get();
            attachAbility(target, new StockAbilityCantAttack(), ChronoDuration.turnEnd());
        }
    }
}
