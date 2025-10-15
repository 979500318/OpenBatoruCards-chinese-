package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.Ability.AbilityFlag;
import open.batoru.data.ability.AbilityCostList;
import open.batoru.data.ability.ConstantAbility;
import open.batoru.data.ability.cost.DownCost;
import open.batoru.data.ability.cost.TrashCost;
import open.batoru.data.ability.modifiers.PowerModifier;

public final class SIGNI_W2_KazusaKyouyama extends Card {

    public SIGNI_W2_KazusaKyouyama()
    {
        setImageSets("WXDi-CP02-069");

        setOriginalName("杏山カズサ");
        setAltNames("キョウヤマカズサ Kyouyama Kazusa");
        setDescription("jp",
                "@A #D @[エナゾーンから＜ブルアカ＞のカード１枚をトラッシュに置く]@：対戦相手のレベル１のシグニ１体を対象とし、それを手札に戻す。" +
                "~{{C：このシグニのパワーは＋4000される。"
        );

        setName("en", "Kyoyama Kazusa");
        setDescription("en",
                "@A #D @[Put a <<Blue Archive>> card from your Ener Zone into your trash]@: Return target level one SIGNI on your opponent's field to its owner's hand.~{{C: This SIGNI gets +4000 power."
        );
        
        setName("en_fan", "Kazusa Kyouyama");
        setDescription("en_fan",
                "@A #D @[Put 1 <<Blue Archive>> card from your ener zone into the trash]@: Target 1 of your opponent's level 1 SIGNI, and return it to their hand." +
                "~{{C: This SIGNI gets +4000 power."
        );

		setName("zh_simplified", "杏山千纱");
        setDescription("zh_simplified", 
                "@A #D从能量区把<<ブルアカ>>牌1张放置到废弃区:对战对手的等级1的精灵1只作为对象，将其返回手牌。\n" +
                "~{{C:这只精灵的力量+4000。@@\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.WHITE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.BLUE_ARCHIVE);
        setLevel(2);
        setPower(8000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            registerActionAbility(new AbilityCostList(new DownCost(), new TrashCost(new TargetFilter().withClass(CardSIGNIClass.BLUE_ARCHIVE).fromEner())), this::onActionEff);

            ConstantAbility cont = registerConstantAbility(new PowerModifier(4000));
            cont.getFlags().addValue(AbilityFlag.BONDED);
        }
        
        private void onActionEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.HAND).OP().SIGNI().withLevel(1)).get();
            addToHand(target);
        }
    }
}
