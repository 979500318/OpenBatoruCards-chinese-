package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.CardDataColor;
import open.batoru.data.Cost;
import open.batoru.data.DataTable;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.ActionAbility;
import open.batoru.data.ability.cost.EnerCost;
import open.batoru.data.ability.stock.StockAbilityLancer;
import open.batoru.data.ability.stock.StockAbilitySLancer;

import java.util.HashSet;
import java.util.Set;

public final class SIGNI_G3_WOLFFessoneRoaringGun extends Card {

    public SIGNI_G3_WOLFFessoneRoaringGun()
    {
        setImageSets("WXDi-P14-046", "WXDi-P14-046P");

        setOriginalName("轟砲　WOLF//フェゾーネ");
        setAltNames("ゴウホウウルフフェゾーネ Gouhou Urufu Fezoone");
        setDescription("jp",
                "@A %G %X：あなたの場に共通する色を持つルリグが２体以上いる場合、ターン終了時まで、このシグニは【Ｓランサー】を得る。" +
                "~#：どちらか１つを選ぶ。\n" +
                "$$1対戦相手のアップ状態のシグニ１体を対象とし、それをバニッシュする。\n" +
                "$$2カードを１枚引く。"
        );

        setName("en", "WOLF//Fesonne, Roaring Gun");
        setDescription("en",
                "@A %G %X: If there are two or more LRIG that share a color on your field, this SIGNI gains [[S Lancer]] until end of turn. \n " +
                "~#Choose one -- \n$$1Vanish target upped SIGNI on your opponent's field. \n$$2Draw a card."
        );
        
        setName("en_fan", "WOLF//Fessone, Roaring Gun");
        setDescription("en_fan",
                "@A %G %X: If there are 2 or more LRIG that share a common color on your field, until end of turn, this SIGNI gains [[S Lancer]]." +
                "~#@[@|Choose 1 of the following:|@]@\n" +
                "$$1 Target 1 of your opponent's upped SIGNI, and banish it.\n" +
                "$$2 Draw 1 card."
        );

		setName("zh_simplified", "轰炮 WOLF//音乐节");
        setCardFlags(CardFlag.LIFEBURST);

        setType(CardType.SIGNI);
        setColor(CardColor.GREEN);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.WEAPON);
        setLevel(3);
        setPower(12000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            ActionAbility act = registerActionAbility(new EnerCost(Cost.color(CardColor.GREEN, 1) + Cost.colorless(1)), this::onActionEff);
            act.setCondition(this::onActionEffCond);
            
            registerLifeBurstAbility(this::onLifeBurstEff);
        }

        private ConditionState onActionEffCond()
        {
            return getCardIndex().getIndexedInstance().getAbilityList().stream().anyMatch(ability ->
                    ability.getSourceStockAbility() instanceof StockAbilityLancer ||
                    ability.getSourceStockAbility() instanceof StockAbilitySLancer) ? ConditionState.WARN : ConditionState.OK;
        }
        private void onActionEff()
        {
            DataTable<CardIndex> data = getLRIGs(getOwner());
            if(data.size() < 2) return;
            
            Set<CardColor> cacheColors = new HashSet<>();
            for(int i=0;i<data.size();i++)
            {
                CardDataColor color = data.get(i).getIndexedInstance().getColor();
                if(color.matches(cacheColors))
                {
                    attachAbility(getCardIndex(), new StockAbilitySLancer(), ChronoDuration.turnEnd());
                    break;
                }
                
                cacheColors.addAll(color.getValue());
            }
        }
        
        private void onLifeBurstEff()
        {
            if(playerChoiceMode() == 1)
            {
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().upped()).get();
                banish(target);
            } else {
                draw(1);
            }
            }
    }
}
