package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.Ability.AbilityFlag;
import open.batoru.data.ability.AbilityCostList;
import open.batoru.data.ability.ConstantAbility;
import open.batoru.data.ability.cost.DiscardCost;
import open.batoru.data.ability.cost.DownCost;
import open.batoru.data.ability.cost.EnerCost;
import open.batoru.data.ability.modifiers.PowerModifier;

public final class SIGNI_W2_MisakiImashino extends Card {

    public SIGNI_W2_MisakiImashino()
    {
        setImageSets("WX25-CP1-059");

        setOriginalName("戒野ミサキ");
        setAltNames("イマシノミサキ Imashino Misaki");
        setDescription("jp",
                "@A %W #D @[手札から＜ブルアカ＞のカードを１枚捨てる]@：対戦相手のパワー10000以下のシグニ１体を対象とし、それを手札に戻す。" +
                "~{{C：このシグニのパワーは＋4000される。"
        );

        setName("en", "Imashino Misaki");

        setName("en_fan", "Misaki Imashino");
        setDescription("en",
                "@A %W #D @[Discard 1 <<Blue Archive>> card from your hand]@: Target 1 of your opponent's SIGNI with power 10000 or less, and return it to their hand." +
                "~{{C: This SIGNI gets +4000 power."
        );

		setName("zh_simplified", "戒野美咲");
        setDescription("zh_simplified", 
                "@A %W#D从手牌把<<ブルアカ>>牌1张舍弃:对战对手的力量10000以下的精灵1只作为对象，将其返回手牌。\n" +
                "~{{C:这只精灵的力量+4000。@@\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.WHITE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.BLUE_ARCHIVE);
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
            
            registerActionAbility(new AbilityCostList(
                new EnerCost(Cost.color(CardColor.WHITE, 1)),
                new DownCost(),
                new DiscardCost(new TargetFilter().withClass(CardSIGNIClass.BLUE_ARCHIVE))
            ), this::onActionEff);
            
            ConstantAbility cont = registerConstantAbility(new PowerModifier(4000));
            cont.getFlags().addValue(AbilityFlag.BONDED);
        }

        private void onActionEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.HAND).OP().SIGNI().withPower(0,10000)).get();
            addToHand(target);
        }
    }
}
