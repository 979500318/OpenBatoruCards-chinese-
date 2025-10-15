package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.stock.StockAbilityShadow;

public final class SIGNI_G1_UgokueDissonaVerdantBeauty extends Card {

    public SIGNI_G1_UgokueDissonaVerdantBeauty()
    {
        setImageSets("WXDi-P12-077", "SPDi38-28");

        setOriginalName("翠美　ウゴクエ//ディソナ");
        setAltNames("スイビウゴクエディソナ Suibi Ugokue Disona");
        setDescription("jp",
                "@C：[[シャドウ（パワー3000以下のシグニ）]]" +
                "~#：【エナチャージ１】をする。このターン、次にあなたがシグニによってダメージを受ける場合、代わりにダメージを受けない。"
        );

        setName("en", "Moving Picture//Dissona, Jade Beauty");
        setDescription("en",
                "@C: [[Shadow -- SIGNI with power 3000 or less]]. " +
                "~#[[Ener Charge 1]]. The next time you would take damage from a SIGNI this turn, instead you do not take that damage."
        );
        
        setName("en_fan", "Ugokue//Dissona, Verdant Beauty");
        setDescription("en_fan",
                "@C: [[Shadow (SIGNI with power 3000 or less)]]" +
                "~#[[Ener Charge 1]]. This turn, the next time you would be damaged by a SIGNI, instead you aren't damaged."
        );

		setName("zh_simplified", "翠美 会动肖像//失调");
        setDescription("zh_simplified", 
                "@C :[[暗影（力量3000以下的精灵）]]（这只精灵不会被对战对手的力量3000以下的精灵作为对象）" +
                "~#[[能量填充1]]。这个回合，下一次你因为精灵受到伤害的场合，作为替代，不会受到伤害。\n"
        );

        setCardFlags(CardFlag.LIFEBURST | CardFlag.DISSONA);

        setType(CardType.SIGNI);
        setColor(CardColor.GREEN);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.BEAUTIFUL_TECHNIQUE);
        setLevel(1);
        setPower(3000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            registerStockAbility(new StockAbilityShadow(this::onStockEffAddCond));

            registerLifeBurstAbility(this::onLifeBurstEff);
        }
        
        private ConditionState onStockEffAddCond(CardIndex cardIndexSource)
        {
            return CardType.isSIGNI(cardIndexSource.getCardReference().getType()) &&
                   cardIndexSource.getIndexedInstance().getPower().getValue() <= 3000 ? ConditionState.OK : ConditionState.BAD;
        }

        private void onLifeBurstEff()
        {
            enerCharge(1);

            blockNextDamage(cardIndexSnapshot -> CardType.isSIGNI(cardIndexSnapshot.getCardReference().getType()));
        }
    }
}
