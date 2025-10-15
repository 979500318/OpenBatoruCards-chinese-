package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardLRIGType;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.Cost;
import open.batoru.data.DataTable;
import open.batoru.data.ability.Ability.AbilityFlag;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.ActionAbility;
import open.batoru.data.ability.cost.EnerCost;
import open.batoru.data.ability.stock.StockAbilitySLancer;
import open.batoru.data.CardDataImageSet.Mask;

public final class LRIG_G3_HoshinoTakanashiSwimsuitAquaticSupport extends Card {

    public LRIG_G3_HoshinoTakanashiSwimsuitAquaticSupport()
    {
        setImageSets(Mask.PORTRAIT_OFFSET_CENTER_RIGHT+"WX25-CD1-04");

        setOriginalName("小鳥遊ホシノ(水着)[水上支援]");
        setAltNames("タカナシホシノミズギスイジョウシエン Takanashi Hoshio Mizugi Suijou Shien");
        setDescription("jp",
                "@A $T1 %G %X：あなたの緑のシグニ１体を対象とし、ターン終了時まで、それは【Ｓランサー】を得る。\n" +
                "@A $G1 %G %X：あなたのデッキをシャッフルし一番上のカードをライフクロスに加える。" +
                "~{{A $G1 %G0：あなたのエナゾーンからシグニを３枚まで対象とし、それらを場に出す。ターン終了時まで、あなたのすべてのシグニのパワーを＋3000する。"
        );

        setName("en", "Hoshino Takanashi (Swimsuit) [Aquatic Support]");
        setDescription("en",
                "@A $T1 %G %X: Target 1 of your green SIGNI, and until end of turn, it gains [[S Lancer]].\n" +
                "@A $G1 %G %X: Shuffle your deck, and add the top card of your deck to life cloth." +
                "~{{A $G1 %G0: Target up to 3 SIGNI from your ener zone, and put them onto the field. Until end of turn, all of your SIGNI get +3000 power."
        );

		setName("zh_simplified", "小鸟游星野(泳装)[水上支援]");
        setDescription("zh_simplified", 
                "@A $T1 %G%X:你的绿色的精灵1只作为对象，直到回合结束时为止，其得到[[S枪兵]]。\n" +
                "@A $G1 %G%X:你的牌组洗切把最上面的牌加入生命护甲。\n" +
                "~{{A$G1 %G0:从你的能量区把精灵3张最多作为对象，将这些出场。直到回合结束时为止，你的全部的精灵的力量+3000。@@\n"
        );

        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.HOSHINO);
        setColor(CardColor.GREEN);
        setCost(Cost.color(CardColor.GREEN, 2));
        setLevel(3);
        setLimit(6);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }
    
    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            ActionAbility act1 = registerActionAbility(new EnerCost(Cost.color(CardColor.GREEN, 1) + Cost.colorless(1)), this::onActionEff1);
            act1.setUseLimit(UseLimit.TURN, 1);
            
            ActionAbility act2 = registerActionAbility(new EnerCost(Cost.color(CardColor.GREEN, 1) + Cost.colorless(1)), this::onActionEff2);
            act2.setUseLimit(UseLimit.GAME, 1);
            
            ActionAbility act3 = registerActionAbility(new EnerCost(Cost.color(CardColor.GREEN, 0)), this::onActionEff3);
            act3.setUseLimit(UseLimit.GAME, 1);
            act3.getFlags().addValue(AbilityFlag.BONDED);
        }
        
        private void onActionEff1()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.ABILITY).own().SIGNI().withColor(CardColor.GREEN)).get();
            attachAbility(target, new StockAbilitySLancer(), ChronoDuration.turnEnd());
        }
        
        private void onActionEff2()
        {
            shuffleDeck();
            addToLifeCloth(1);
        }
        
        private void onActionEff3()
        {
            DataTable<CardIndex> data = playerTargetCard(0,3, new TargetFilter(TargetHint.FIELD).own().SIGNI().fromEner().playable());
            putOnField(data);
            
            gainPower(getSIGNIOnField(getOwner()), 3000, ChronoDuration.turnEnd());
        }
    }
}
